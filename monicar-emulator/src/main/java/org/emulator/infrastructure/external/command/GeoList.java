package org.emulator.infrastructure.external.command;

/**
 * 지오펜스 정보 데이터를 담는 레코드
 *
 * @param geoCtrId 지오펜스 아이디 - 지오펜스를 식별하는 고유 아이디
 * @param upVal 업데이트 값 - 전체 업데이트 상태값 (‘0’: 전체 업데이트 미설정, ‘1’: 전체 업데이트 설정)
 * @param geoGrpId 그룹 아이디 - 알림지역 설정 여부 (‘1’: 알림지역 설정)
 * @param geoEvtTp 이벤트 타입 - 이벤트 판단 방식 (‘1’: 진입만 판단, ‘2’: 이탈만 판단, ’3’: 진입/이탈 모두 판단)
 * @param geoRange 지오펜스 반경 - 지오펜스의 반경 (단위: 미터)
 * @param lat GPS 위도 - 차량의 현재 위도 좌표
 * @param lon GPS 경도 - 차량의 현재 경도 좌표
 * @param onTime 지오펜스 시작 시간
 * @param offTime 지오펜스 종료 시간
 * @param storeTp 저장 타입 - 데이터의 저장 동작 (‘1’: 추가, ‘2’: 삭제)
 */
public record GeoList(
    String geoCtrId,
    String upVal,
    String geoGrpId,
    String geoEvtTp,
    String geoRange,
    String lat,
    String lon,
    String onTime,
    String offTime,
    String storeTp
) {}
