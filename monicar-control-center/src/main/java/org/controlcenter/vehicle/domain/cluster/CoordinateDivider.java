package org.controlcenter.vehicle.domain.cluster;

public class CoordinateDivider {

	public static GeoCoordinate[][] generateGridCoordinates(GeoCoordinate ne, GeoCoordinate sw, int rows, int cols) {
		GeoCoordinate[][] gridCoordinates = new GeoCoordinate[rows + 1][cols + 1];

		// 우상단
		int neLat = ne.getLat();
		int neLng = ne.getLng();

		// 좌하단
		int swLat = sw.getLat();
		int swLng = sw.getLng();

		int[] rowArray = new int[rows + 1];
		rowArray[rows] = neLat;
		int width = (neLat - swLat) / rows;
		for (int i = 0; i < rows; i++) {
			rowArray[i] = swLat + (i * width);
		}

		int[] colArray = new int[cols + 1];
		colArray[cols] = neLng;
		int height = (neLng - swLng) / cols;
		for (int i = 0; i < cols; i++) {
			colArray[i] = swLng + (i * height);
		}

		for (int i = 0; i < rowArray.length; i++) {
			for (int j = 0; j < colArray.length; j++) {
				gridCoordinates[i][j] = new GeoCoordinate(rowArray[i], colArray[j]);
			}
		}

		return gridCoordinates;
	}
}
