package com.project.search.util.exception;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //400 BAD_REQUEST 잘못된 요청
	BAD_REQUEST(400, "잘못된 요청입니다."),

    //500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(500, "서버에 문제가 발생하였습니다.");
    
	@Getter
    private final int status;
	@Getter
    private final String message;
    
	//status code를 이용하여 enum 값을 가져오기 위해 세팅
    private static final Map<Integer, String> codeMap = Collections.unmodifiableMap(Stream.of(values()).collect(Collectors.toMap(ErrorCode::getStatus, ErrorCode::name)));
    
    public static ErrorCode valueOfStatus(int httpStatus) {
    	return ErrorCode.valueOf(codeMap.get(httpStatus));
    }
}