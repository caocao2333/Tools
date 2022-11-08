/**
 * This is a Caesar¡¯s decipher that automatically find the most likely key
 * By finding the smallest delta between the average use of the letters on
 * English texts and the encrypted text with an offset k.
 * @date 11/08/2022
 */

import java.text.*;

public class CaesarsDecipher {
	private static final double[] ref = new double[] {8.1, 1.5, 2.7, 4.2, 12.7, 2.2, 2, 6, 6.9, 0.1, 0.7, 4, 2.4, 6.7, 7.5, 1.9, 0.1, 5.9, 6.3, 9, 2.7, 0.1, 2.3, 0.1, 1.9, 0.07};
	private static final DecimalFormat df1 = new DecimalFormat("0.00");
	private static final DecimalFormat df2 = new DecimalFormat("##.0");
	private static double[] freq = new double[26];
	private static int likelyKey;
	
	public static void main(String[] args) {
		String s = String.join("", args);
		s = s.replaceAll("[^A-Za-z]+", "").toLowerCase();	
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			freq[c - 97] += 1;
		}
		for(int i = 0; i < freq.length; i++) freq[i] = (freq[i] / s.length()) * 100;
		for(int i = 0; i < 26; i++) System.out.print((char)(i + 97) + "    ");
		System.out.println();
		for(double d : freq) {
			if(d >= 10) System.out.print(df2.format(d) + " ");
			else System.out.print(df1.format(d) + " ");
		}
		System.out.println();
		int k = 0;
		double curMin = Double.MAX_VALUE;
		for(int i = 0; i < 26; i++) {
			double delta = 0;
			for(int f = 0; f < 26; f++) delta += Math.abs(ref[f] - freq[(f + k) % 26]);
			if(delta < curMin) {
				curMin = delta;
				likelyKey = k;
			}
			k++;
		}
		System.out.print("Most likely key (shifted forward): " + likelyKey);
		System.out.println("\nAfter decoding:");
		for(int i = 0; i < s.length(); i++) System.out.print((char)(((s.charAt(i) - likelyKey - 71) % 26) + 97));
	}
}
