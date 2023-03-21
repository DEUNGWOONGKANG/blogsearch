package com.project.search.util.exception;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //400 BAD_REQUEST �߸��� ��û
	BAD_REQUEST(400, "�߸��� ��û�Դϴ�."),

    //500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(500, "������ ������ �߻��Ͽ����ϴ�.");
    
	@Getter
    private final int status;
	@Getter
    private final String message;
    
	//status code�� �̿��Ͽ� enum ���� �������� ���� ����
    private static final Map<Integer, String> codeMap = Collections.unmodifiableMap(Stream.of(values()).collect(Collectors.toMap(ErrorCode::getStatus, ErrorCode::name)));
    
    public static ErrorCode valueOfStatus(int httpStatus) {
    	return ErrorCode.valueOf(codeMap.get(httpStatus));
    }
}