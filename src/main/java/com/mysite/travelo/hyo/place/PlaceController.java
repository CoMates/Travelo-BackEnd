package com.mysite.travelo.hyo.place;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.mysite.travelo.yeon.user.SiteUser;
import com.mysite.travelo.yeon.user.UserService;

/* *
 * 장소 컨트롤러
 * list : 장소 전체 리스트를 가져오는 기능
 * url 형식 : /place/list?item=&page=&keyword=&sorts=&content=&area=
 * 기본값
 * : item (페이지 당 아이템 수) = 15
 * : page (페이지 번호, 0부터 시작) = 0
 * : keyword (검색어, 장소명 한정) = ""
 * : sorts (정렬, popular만 가능) = ""
 * : content (장소 유형) = ""
 * : area (지역 코드) = ""
 *
 * distance : 거리 기준으로 장소 리스트를 가져오는 기능
 * url 형식 : /place/distance?item=&page=&keyword=&contentId=&distance=&content=
 * 기본값
 * : item (페이지 당 아이템 수) = 15
 * : page (페이지 번호, 0부터 시작) = 0
 * : keyword (검색어, 장소명 한정) = ""
 * : contentId (콘텐츠 번호, 각 장소당 하나 씩 할당) : 2733967 (가회동 성당)
 * : distance (반경 거리, km 기준. 반지름임.) : 20.0
 * : content (장소 유형) : ""
 *
 * increaseLike, decreaseLike : 좋아요 증가 감소 기능
 * : 상태 관리는 프론트에서 해야함.
 *
 * viewCount : 조회수 기능
 * : 계정 당 1회는 실패함. 새로고침하면 계속 늘어요
 *
 */

@RestController
@RequiredArgsConstructor
public class PlaceController {

	private final UserService userService;
	private final PlaceService placeService;
    
//	   좋아요 상태 전환
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/user/place/like/{contentId}")
    public ResponseEntity<String> toggleLike(@PathVariable("contentId") String contentId,
                                          Authentication auth) {
	 	
	     SiteUser loginUser = userService.getLoginUserByUsername(auth.getName());
	     
	     String likeYn = placeService.togglePlaceLike(contentId, loginUser);
	     
	     return new ResponseEntity<>(likeYn, HttpStatus.OK);
 }

}
