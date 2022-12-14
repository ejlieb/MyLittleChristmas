package com.ssafy.doyouwannabuildasnowball.service;

import com.ssafy.doyouwannabuildasnowball.common.exception.CustomException;
import com.ssafy.doyouwannabuildasnowball.domain.Member;
import com.ssafy.doyouwannabuildasnowball.domain.Music;
import com.ssafy.doyouwannabuildasnowball.domain.Snowglobe;
import com.ssafy.doyouwannabuildasnowball.domain.collection.Element;
import com.ssafy.doyouwannabuildasnowball.domain.collection.Decoration;
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
import com.ssafy.doyouwannabuildasnowball.repository.jpa.MemberRepository;
import com.ssafy.doyouwannabuildasnowball.repository.jpa.MusicRepository;
import com.ssafy.doyouwannabuildasnowball.repository.jpa.SnowglobeRepository;
import com.ssafy.doyouwannabuildasnowball.repository.mongo.DecorationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.ssafy.doyouwannabuildasnowball.common.exception.ErrorCode.*;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SnowglobeService {
    private final MemberRepository memberRepository;
    private final SnowglobeRepository snowglobeRepository;
    private final DecorationRepository decorationRepository;
    public final MusicRepository musicRepository;

    //?????? ???????????? ?????????
    @Transactional
    public MainSnowglobeDto mainSnowglobe(Long uid) {
        Member member = memberRepository.findById(uid).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        Snowglobe snowglobe = member.getSnowglobe();
        Decoration decoration = decorationRepository.findById(snowglobe.getSnowglobeId()).orElseThrow(() -> new CustomException(DECORATION_NOT_FOUND));

        return MainSnowglobeDto.builder()
                .snowglobeId(snowglobe.getSnowglobeId())
                .deco(decoration.getDeco())
                .musicId(snowglobe.getMusic().getMusicId())
                .build();
    }

    //?????? ???????????? ????????? ?????? ??????
    //url = `https://?????? ??????/share?userId=${userId}`
    @Transactional
    public String shareSnowglobe(Long mid) {
        String url = "https://localhost:8080/share?memberId="+mid;
        return url;
    }

    //?????? ???????????? ??????
    @Transactional
    public void updateSnowglobe(Long uid, SnowglobeUpdateRequestDto snowglobeUpdateRequestDto) {
        //?????? ???????????? ????????? main_id > mid
        Member member = memberRepository.findById(uid).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        Music music = musicRepository.findById(snowglobeUpdateRequestDto.getMusicId()).orElseThrow(() -> new CustomException(MUSIC_NOT_FOUND));
        Snowglobe snowglobe = member.getSnowglobe();
        snowglobe.updateScreenshot(snowglobeUpdateRequestDto.getScreenshot());
        snowglobe.updateMusic(music);

        List<Element> deco = snowglobeUpdateRequestDto.getDeco();

        Decoration decoById = decorationRepository.findById(snowglobe.getSnowglobeId()).orElseThrow(() -> new CustomException(DECORATION_NOT_FOUND));
        decoById.updateDeco(snowglobe.getSnowglobeId(), deco);
        
        decorationRepository.save(decoById);
        snowglobeRepository.save(snowglobe);
    }

    //???????????? ????????? ??????
    @Transactional
    public void modifyCoordinate(Long sid, SnowglobeCoordinateModifyRequestDto snowglobeCoordinateModifyRequestDto) {
        Decoration decoration = decorationRepository.findById(sid).orElseThrow(() -> new CustomException(DECORATION_NOT_FOUND));
        decoration.updateDeco(sid, snowglobeCoordinateModifyRequestDto.getDeco());
        decorationRepository.save(decoration);
    }

    //???????????? ??????
    @Transactional
    public void changeScreenshot(SnowglobeScreenshotRequestDto snowglobeScreenshotRequestDto) {
        Snowglobe snowglobe = snowglobeRepository.findById(snowglobeScreenshotRequestDto.getSid()).orElseThrow(() -> new CustomException(SNOWGLOBE_NOT_FOUND));
        snowglobe.updateScreenshot(snowglobeScreenshotRequestDto.getUrl());
        snowglobeRepository.save(snowglobe);
    }

    //?????? ?????? ??????????????? ???????????? ????????????
    @Transactional
    public Long presentSnowglobe(Long rid, SnowglobeRequestDto snowglobeRequestDto) {
        Member maker = memberRepository.findById(snowglobeRequestDto.getMakerId()).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        Member receiver = memberRepository.findById(rid).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        Music music = musicRepository.findById(snowglobeRequestDto.getMusicId()).orElseThrow(() -> new CustomException(MUSIC_NOT_FOUND));
        Snowglobe snowglobe = Snowglobe.builder()
                .maker(maker)
                .screenshot(snowglobeRequestDto.getScreenshot())
                .receiver(receiver)
                .makerSaved(false)
                .receiverSaved(true)
                .music(music)
                .build();

        snowglobeRepository.save(snowglobe);

        Decoration decoration = Decoration.builder()
                .id(snowglobe.getSnowglobeId())
                .deco(snowglobeRequestDto.getDeco())
                .build();

        decorationRepository.save(decoration);

        Long sid = snowglobe.getSnowglobeId();
        return sid;
    }

    //????????? ???????????? ??? ????????? ??????
    @Transactional
    public void savePresent(Long snowglobeId) {
        Snowglobe snowglobe = snowglobeRepository.findById(snowglobeId).orElseThrow(() -> new CustomException(SNOWGLOBE_NOT_FOUND));
        if (!snowglobe.isMakerSaved()) {
            snowglobe.updateMakerSaved(!snowglobe.isMakerSaved());
        } else {
            log.info("?????? ????????? ?????????????????????.");
        }
        snowglobeRepository.save(snowglobe);
    }

    //???????????? ???????????? ?????? (??? ?????? / ?????? ???????????? ??????)
    @Transactional
    public List<SnowglobeCollectionResponseDto> showAllSnowglobe(Long uid) {
        Member member = memberRepository.findById(uid).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        List<SnowglobeShelfResponseDto> temp = new ArrayList<SnowglobeShelfResponseDto>(snowglobeRepository.findAllByMakerIdAndReceiverId(uid));

        List<SnowglobeCollectionResponseDto> result = new ArrayList<SnowglobeCollectionResponseDto>();
        for (int i=0; i<temp.size(); i++) {
            Member maker = memberRepository.findById(temp.get(i).getMakerId()).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
            result.add(SnowglobeCollectionResponseDto.builder()
                    .snowglobeId(temp.get(i).getSnowglobeId())
                    .screenshot(temp.get(i).getScreenshot())
                    .maker(maker.getNickname())
                    .build());
        }

        return result;
    }

    //???????????? ???????????? ??????
    @Transactional
    public void deleteSnowglobe(Long sid, Long mid) {
        Snowglobe snowglobe = snowglobeRepository.findById(sid).orElseThrow(() -> new CustomException(SNOWGLOBE_NOT_FOUND));
        if (mid.equals(snowglobe.getMaker().getMemberId())) {
            snowglobe.updateMakerSaved(false);
            snowglobeRepository.save(snowglobe);
        } else if (mid.equals(snowglobe.getReceiver().getMemberId())) {
            snowglobe.updateReceiverSaved(false);
            snowglobeRepository.save(snowglobe);
        } else {
            throw new CustomException(SNOWGLOBE_NOT_FOUND);
        }
    }

    //???????????? ?????? (????????? ????????????)
    @Transactional
    public SnowglobeDetailResponseDto showDetail(Long sid) {
        Snowglobe snowglobe = snowglobeRepository.findById(sid).orElseThrow(() -> new CustomException(SNOWGLOBE_NOT_FOUND));
        Decoration decoration = decorationRepository.findById(sid).orElseThrow(() -> new CustomException(DECORATION_NOT_FOUND));
        return SnowglobeDetailResponseDto.builder()
                .receiverId(snowglobe.getReceiver().getMemberId())
                .snowglobeId(sid)
                .musicId(snowglobe.getMusic().getMusicId())
                .deco(decoration.getDeco())
                .build();
    }

    //?????? ?????? ??????
    @Transactional
    public List<MusicAllDto> musicAll() {
        List<MusicAllDto> result = new ArrayList<>();
        List<Music> musicList = musicRepository.findAll();
        for (Music music : musicList) {
            result.add(MusicAllDto.builder()
                    .musicId(music.getMusicId())
                    .title(music.getTitle())
                    .url(music.getUrl())
                    .build());
        }
        return result;
    }

    //?????? ??????
    @Transactional
    public void musicSelect(Long sid, MusicSelectRequestDto musicSelectRequestDto) {
        Snowglobe snowglobe = snowglobeRepository.findById(sid).orElseThrow(() -> new CustomException(SNOWGLOBE_NOT_FOUND));
        Music music = musicRepository.findById(musicSelectRequestDto.getMusicId()).orElseThrow(() -> new CustomException(MUSIC_NOT_FOUND));
        snowglobe.updateMusic(music);
        snowglobeRepository.save(snowglobe);
    }

}
