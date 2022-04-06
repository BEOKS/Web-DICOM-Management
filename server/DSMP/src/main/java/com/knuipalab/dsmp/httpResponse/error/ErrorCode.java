package com.knuipalab.dsmp.httpResponse.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    DEFAULT_BAD_REQUEST(HttpStatus.BAD_REQUEST,"잘못된 요청으로 인해 등록이 불가능 합니다."),
    USER_EMAIL_BAD_REQUEST(HttpStatus.BAD_REQUEST,"해당 EMAIL 로 가입된 USER 정보가 없습니다."),
    EMPTY_FILE_BAD_REQUEST(HttpStatus.BAD_REQUEST,"빈 파일에 대해서는 참조할 수 없습니다."),

    /* 401 UNAUTHORIZED : 권한이 없는 접근 */
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "해당 접근에 대한 권한이 없습니다."),

    /* 403 FORBIDDEN : 해당 권한으로 접근이 허락 되지 않는 정보*/
//    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "해당 접근은 권한이 없어 접근이 불가능합니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER 를 찾을 수 없습니다."),
    METADATA_NOT_FOUND(HttpStatus.NOT_FOUND,"METADATA 를 찾을 수 없습니다."),
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND,"PROJECT 를 찾을 수 없습니다."),

    /* 409 CONFLICT : DB 데이터 관리 충돌 */
    CONSTRAINT_VIOLATION_RESOURCE(HttpStatus.CONFLICT, "DB 제약조건 위반입니다."),
    INTEGRITY_VIOLATION_RESOURCE(HttpStatus.CONFLICT, "DB 무결성 위반입니다."),

    /* 500 핸들링 하지 않은 에러 */
    NULL_POINTER(HttpStatus.INTERNAL_SERVER_ERROR,"NULL 포인터에 대한 접근이 있습니다."),
    MAX_UPLOAD_SIZE(HttpStatus.INTERNAL_SERVER_ERROR,"업로드 가능한 최대용량을 초과 했습니다."),
    FILE_IO_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR,"File IO 과정에서 오류가 발생했습니다.")

    ;

    private final HttpStatus httpStatus;
    private final String detail;
    private List<Object> failList;

    ErrorCode(HttpStatus httpStatus, String detail){
        this.httpStatus = httpStatus;
        this.detail = detail;
    }

    ErrorCode(HttpStatus httpStatus, String detail,List<Object> failList){
        this.httpStatus = httpStatus;
        this.detail = detail;
        this.failList = failList;
    }

    public void setFailList(List<Object> failList) {
        this.failList = failList;
    }
}