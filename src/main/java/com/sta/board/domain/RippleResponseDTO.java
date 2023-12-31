package com.sta.board.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import com.sta.security.domain.User;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RippleResponseDTO {
	private Long ri_id;
	private String ri_content;
	private LocalDateTime ri_createdAt;
	
	private Long boardid;
	private Long id;
	private String nickname;
	private String img;
	private String timeDifferences;
	

	private static final int SEC = 60;
	private static final int MIN = 60;
	private static final int HOUR = 24;
	private static final int DAY = 30;
	private static final int MONTH = 12;
	
	
	
	@Builder
	public RippleResponseDTO(Ripple ripple) {
		this.ri_id = ripple.getRi_id();
		this.ri_content = ripple.getRi_content();
		this.ri_createdAt = ripple.getRi_createdAt();
		
		this.boardid = ripple.getBoard().getBoardid();
		this.id = ripple.getUser().getId();
		this.nickname = ripple.getUser().getNickname();
		this.img = ripple.getUser().getImg();
		this.timeDifferences = calculateTimeDifferences();
		
	}
	
	
	
	
	public String calculateTimeDifferences() {
	   
	    ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

	    ZonedDateTime createdDateTime = ri_createdAt.atZone(ZoneId.of("Asia/Seoul"));

	    // 두 날짜를 비교하여 현재 시간이 미래라면 방금 전으로 처리
	  
	        long diffInMillies = ChronoUnit.MILLIS.between(createdDateTime, currentDateTime);

	        // 초 단위로 변환
	        long diffInSeconds = diffInMillies / 1000;
	        

	        if (diffInSeconds < SEC) {
	            return diffInSeconds + "초 전";
	        } else if ((diffInSeconds /= MIN) < MIN) {
	            return diffInSeconds + "분 전";
	        } else if ((diffInSeconds /= HOUR) < HOUR) {
	            return diffInSeconds + "시간 전";
	        } else if ((diffInSeconds /= DAY) < DAY) {
	            return diffInSeconds + "일 전";
	        } else if ((diffInSeconds /= MONTH) < MONTH) {
	            return diffInSeconds + "달 전";
	        } else {
	            return diffInSeconds + "년 전";
	        }
	   
	}

}
