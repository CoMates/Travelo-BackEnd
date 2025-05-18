package com.mysite.travelo.hyo.place;

import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mysite.travelo.gil.course.Course;
import com.mysite.travelo.gil.course.CourseLike;
import com.mysite.travelo.yeon.user.SiteUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* *
 * 장소 서비스
 * 1. List<Place> findAll() : 리스트의 형태로 전체를 가져옴
 *
 * 2. Page<Place> getPage(int page, String keyword, String sort, List<String> content_list, List<String> area_list)
 * : 페이지를 가져옴
 * : 입력 받을 내용: 페이지 당 아이템 수, 페이지 번호, 키워드, 정렬, 콘텐츠 코드, 지역 코드
 * : url 형식 : /place/list?item=&page=&keyword=&sorts=&content=&area=
 *
 * 3. Page<Place> getPage(int page, String keyword, double latitude, double longitude, double distance, List<String> content_list)
 * : 특정 컨텐츠 ID를 기준으로 입력 받은 반경 거리 내에서 키워드, 장소 유형에 따라 가까운 순으로 정렬하는 기능
 * : 입력 받을 내용: 페이지 당 아이템 수, 페이지 번호, 키워드, 콘텐츠 ID(콘텐츠 ID를 통해서 latitude, longitude 추출), 반경 거리, 콘텐츠 코드
 * : url 형식 : /place/distance?item=&page=&keyword=&contentId=&distance=&content=
 *
 * 4-1. increaseLike(int placeSeq)
 * : 좋아요 증가 기능
 * : 입력 받을 내용: 장소 순차번호 (콘텐츠 ID 아님. 주의)
 *
 * 4-2. decreaseLike(int placeSeq)
 * : 좋아요 해제 기능
 * : 입력 받을 내용: 장소 순차번호 (콘텐츠 ID 아님. 주의)
 *
 * 5. increaseViewCount(int placeSeq)
 * : 조회수 증가 기능
 * : 입력 받을 내용: 장소 순차번호 (콘텐츠 ID 아님. 주의)
 *
 * Specification<Place> search(keyword) : 검색하는 기능
 * Specification<Place> content : 카테고리 - 장소 유형
 * Specification<Place> area : 카테고리 - 지역
 * Specification<Place> distance : 반경 계산 기능
 */

@RequiredArgsConstructor
@Service
public class PlaceService {

    private final PlaceLikeRepository placeLikeRepository;



//	좋아요 상태관리
    public String togglePlaceLike(String contentId, SiteUser user) {
        // Place 엔티티를 가져옴
//        Optional<Place> op = placeRepository.findByPlaceSeq(placeSeq);
//        Place place = op.get();

        // 이미 좋아요가 존재하는지 확인
        Optional<PlaceLike> opl = placeLikeRepository.findByContentIdAndAuthor(contentId, user);

        String likeYn;

        if (opl.isPresent()) {
            PlaceLike placeLike = opl.get();
            if ("Y".equals(placeLike.getLikeYn())) {
                // 현재 좋아요 상태면, 좋아요 취소로 변경 및 좋아요 갯수 감소
                placeLike.setLikeYn("N");
                likeYn = "N";
            } else {
                // 현재 좋아요 취소 상태면, 좋아요로 변경 및 좋아요 갯수 증가
                placeLike.setLikeYn("Y");
                likeYn = "Y";
            }
            placeLikeRepository.save(placeLike);
        } else {
            // 좋아요가 존재하지 않을 경우 새로 추가
            PlaceLike placeLike = new PlaceLike();
            placeLike.setContentId(contentId);
            placeLike.setAuthor(user);
            placeLike.setLikeYn("Y");
            likeYn = "Y";
            placeLikeRepository.save(placeLike);
        }

        return likeYn;
    }

    // 인기순
    public List<String> findPopularPlaces() {

        Pageable pageable = PageRequest.of(0, 6);

        return placeLikeRepository.findTopContentIds(pageable);

    }





}
