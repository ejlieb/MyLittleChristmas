package com.ssafy.doyouwannabuildasnowball.controller;

import com.ssafy.doyouwannabuildasnowball.config.security.oauth.userinfo.CustomUserDetails;
import com.ssafy.doyouwannabuildasnowball.dto.music.common.MusicAllDto;
import com.ssafy.doyouwannabuildasnowball.dto.music.request.MusicSelectRequestDto;
import com.ssafy.doyouwannabuildasnowball.dto.snowglobe.common.MainSnowglobeDto;
import com.ssafy.doyouwannabuildasnowball.dto.snowglobe.request.SnowglobeCoordinateModifyRequestDto;
import com.ssafy.doyouwannabuildasnowball.dto.snowglobe.request.SnowglobeRequestDto;
import com.ssafy.doyouwannabuildasnowball.dto.snowglobe.request.SnowglobeScreenshotRequestDto;
import com.ssafy.doyouwannabuildasnowball.dto.snowglobe.request.SnowglobeUpdateRequestDto;
import com.ssafy.doyouwannabuildasnowball.dto.snowglobe.response.SnowglobeCollectionResponseDto;
import com.ssafy.doyouwannabuildasnowball.dto.snowglobe.response.SnowglobeDetailResponseDto;
import com.ssafy.doyouwannabuildasnowball.dto.snowglobe.response.SnowglobeShelfResponseDto;
import com.ssafy.doyouwannabuildasnowball.service.SnowglobeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/snowglobe")
public class SnowglobeController {
    private final SnowglobeService snowglobeService;


    //메인 스노우볼 페이지*
    @GetMapping("/{member_id}")
    public ResponseEntity<MainSnowglobeDto> mainSnowglobe(@PathVariable(value = "member_id") Long uid) {
        return new ResponseEntity<MainSnowglobeDto>(snowglobeService.mainSnowglobe(uid), HttpStatus.OK);
    }

    //메인 스노우볼 페이지 링크 공유*
    @GetMapping("/{member_id}/share")
    public ResponseEntity<String> shareSnowglobe(@PathVariable(value = "member_id") Long uid) {
        snowglobeService.shareSnowglobe(uid);
        return new ResponseEntity<String>(snowglobeService.shareSnowglobe(uid), HttpStatus.OK);
    }

    //메인 스노우볼 수정*
    @PutMapping("/{member_id}/modify")
    public ResponseEntity<Void> modifySnowglobe(@PathVariable(value = "member_id") Long uid, @RequestBody SnowglobeUpdateRequestDto snowglobeUpdateRequestDto) {
        snowglobeService.updateSnowglobe(uid, snowglobeUpdateRequestDto);
        return ResponseEntity.ok().build();
    }

    //스노우볼 좌표만 수정*
    @PatchMapping("/{snowglobe_id}/modifyCoordinate")
    public ResponseEntity<Void> modifyCoordinate(@PathVariable(value = "snowglobe_id") Long sid, @RequestBody SnowglobeCoordinateModifyRequestDto snowglobeCoordinateModifyRequestDto) {
        snowglobeService.modifyCoordinate(sid, snowglobeCoordinateModifyRequestDto);
        return ResponseEntity.ok().build();
    }

    //스크린샷 수정*
    @PatchMapping("/changeScreenshot")
    public ResponseEntity<Void> changeScreenshot(@RequestBody SnowglobeScreenshotRequestDto snowglobeScreenshotRequestDto) {
        snowglobeService.changeScreenshot(snowglobeScreenshotRequestDto);
        return ResponseEntity.ok().build();
    }

    //친구 메인 페이지에서 스노우볼 선물하기*
    @PostMapping("/{receiver_id}/present")
    public ResponseEntity<Long> presentSnowglobe(@PathVariable(value = "receiver_id") Long rid, @Valid @RequestBody SnowglobeRequestDto snowglobeRequestDto) {
        return new ResponseEntity<Long>(snowglobeService.presentSnowglobe(rid, snowglobeRequestDto), HttpStatus.OK);
    }

    //선물한 스노우볼 내 책장에 저장하기*
    @PatchMapping("/{snowglobe_id}/save")
    public ResponseEntity<Void> savePresent(@PathVariable(value = "snowglobe_id") Long snowglobeId) {
        snowglobeService.savePresent(snowglobeId);
        return ResponseEntity.ok().build();
    }

    //갖고있는 스노우볼 확인 (내 책장)*
    @GetMapping("/{member_id}/shelf")
    public ResponseEntity<List<SnowglobeCollectionResponseDto>> myShelf(@PathVariable(value = "member_id") Long uid) {
        return new ResponseEntity<List<SnowglobeCollectionResponseDto>>(snowglobeService.showAllSnowglobe(uid), HttpStatus.OK);
    }

    //책장에서 스노우볼 삭제*
    @PatchMapping("/{snowglobe_id}/delete")
    public ResponseEntity<Void> deleteSnowglobe(@PathVariable(value = "snowglobe_id") Long sid, @ApiIgnore @AuthenticationPrincipal CustomUserDetails member) {
        snowglobeService.deleteSnowglobe(sid, member.getId());
        return ResponseEntity.ok().build();
    }

    //스노우볼 상세 (마을로 넘어가기)*
    @GetMapping("/{snowglobe_id}/detail")
    public ResponseEntity<SnowglobeDetailResponseDto> snowglobeDetail(@PathVariable(value = "snowglobe_id") Long sid) {
        return new ResponseEntity<SnowglobeDetailResponseDto>(snowglobeService.showDetail(sid), HttpStatus.OK);
    }

    //음악 목록 조회*
    @GetMapping("/music/list")
    public ResponseEntity<List<MusicAllDto>> musicList() {
        return new ResponseEntity<List<MusicAllDto>>(snowglobeService.musicAll(), HttpStatus.OK);
    }

    //음악 변경*
    @PatchMapping("/{snowglobe_id}/music/select")
    public ResponseEntity<Void> musicChange(@PathVariable(value = "snowglobe_id") Long sid, @Valid @RequestBody MusicSelectRequestDto musicSelectRequestDto) {
        log.info("music controller"+musicSelectRequestDto);
        snowglobeService.musicSelect(sid, musicSelectRequestDto);
        return ResponseEntity.ok().build();
    }

}
