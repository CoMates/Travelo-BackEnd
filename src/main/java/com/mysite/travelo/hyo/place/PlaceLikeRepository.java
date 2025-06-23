package com.mysite.travelo.hyo.place;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mysite.travelo.yeon.user.SiteUser;

public interface PlaceLikeRepository extends JpaRepository<PlaceLike, Integer> {

//	사용자와 장소 순차번호로 좋아요를 찾음
	Optional<PlaceLike> findByContentIdAndAuthor(String contentId, SiteUser author);
	
	@Query("SELECT pl.contentId " +
		       "FROM PlaceLike pl " +
		       "GROUP BY pl.contentId " +
		       "ORDER BY COUNT(pl) DESC")
	List<String> findTopContentIds(Pageable pageable);
	
	@Query(value = """
		    SELECT content_id, COUNT(*) as like_count
		    FROM place_like
		    WHERE like_yn = 'Y'
		    GROUP BY content_id
		""", nativeQuery = true)
	List<Map<String, Integer>> countLikesGroupedByContentId();

}
