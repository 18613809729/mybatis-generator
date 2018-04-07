package com.fuxi.mybatis.generator;

public class Test {

	public int backPack(int m, int[] A) {
		int[] arr = new int[m + 1];
		for (int i = 0; i < A.length; i++) {
			for (int j = m; j >= 0; j--) {
				if (A[i] <= j) {
					arr[j] = Math.max(arr[j], A[i] + arr[j - A[i]]);
				}
			}
		}
		return arr[m];
	}

	public static void main(String[] args) {
		int[] arr = { 828, 125, 740, 724, 983, 321, 773, 678, 841, 842, 875, 377, 674, 144, 340, 467, 625, 916, 463,
				922, 255, 662, 692, 123, 778, 766, 254, 559, 480, 483, 904, 60, 305, 966, 872, 935, 626, 691, 832, 998,
				508, 657, 215, 162, 858, 179, 869, 674, 452, 158, 520, 138, 847, 452, 764, 995, 600, 568, 92, 496, 533,
				404, 186, 345, 304, 420, 181, 73, 547, 281, 374, 376, 454, 438, 553, 929, 140, 298, 451, 674, 91, 531,
				685, 862, 446, 262, 477, 573, 627, 624, 814, 103, 294, 388 };
		System.out.println(new Test().backPack(5000, arr));
	}
}
