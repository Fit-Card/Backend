package com.fitcard.global.util;

public class CoordinateUtil {

    private static final double EARTH_RADIUS = 6371000; // 지구 반지름 (미터)

    // 위도 경도 간의 거리를 계산 (단위: 미터)
    public static double distanceBetweenCoordinates(double lat1, double lon1, double lat2, double lon2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    // 주어진 위도와 경도를 기준으로 bearing 방향으로 distance만큼 이동한 좌표 계산
    public static double[] calculateNewCoordinates(double lat, double lon, double distance, double bearing) {
        double latRad = Math.toRadians(lat);
        double lonRad = Math.toRadians(lon);
        double bearingRad = Math.toRadians(bearing);

        double angularDistance = distance / EARTH_RADIUS;

        double newLatRad = Math.asin(Math.sin(latRad) * Math.cos(angularDistance)
                + Math.cos(latRad) * Math.sin(angularDistance) * Math.cos(bearingRad));

        double newLonRad = lonRad + Math.atan2(Math.sin(bearingRad) * Math.sin(angularDistance) * Math.cos(latRad),
                Math.cos(angularDistance) - Math.sin(latRad) * Math.sin(newLatRad));

        double newLat = Math.toDegrees(newLatRad);
        double newLon = Math.toDegrees(newLonRad);

        return new double[]{newLat, newLon};
    }
}
