package by.bsu.binarytree.alg;

import by.bsu.binarytree.util.SPTreeException;

public class Algorithms {

	private static boolean GetBit(int x, int bit) {
	/*	if (((x & (1 << (bit - 1))) != 0) && GetBitCount(x) == 1)
		System.out.println("x = " + x + " bit = " + bit + " result = " + ((x & (1 << (bit - 1))) != 0));
*/		return (x & (1 << (bit - 1))) != 0;
	}

	public static int GetBitCount(int x) {
		int count = 0;
		for (int i = 1; i < (1 << 6); i = i << 1)
			if ((x & i) != 0)
				count++;
		return count;
	}

	public static int findDiff(char flag, int a, int b, int c) throws SPTreeException {
		switch (flag) {
		case 'p':
			return findDiffP(a, b, c);
		case 's':
			return findDiffS(a, b, c);
		default: throw new SPTreeException("Invalid flag");
		}
	}

	public static StringBuilder gbString(int x, int max) {
		StringBuilder result = new StringBuilder();
		for (int i = 1; i <= max; i++) {
			result.append(GB(x, i) ? 1 : 0);
		}
		return result;
	}
	
	public static boolean GB(int x, int bit)
	{
		return (x & (1<<(bit-1))) != 0;
	}
	
	private static int findDiffP(int a, int b, int c) {
		int ccc = 0;

		// Edge from c cover by edge from a and b

		if (GetBit(a, 1)
				&& GetBit(b, 1)
				|| (GetBit(a, 1) || GetBit(b, 1))
				&& !(GetBit(c, 1) || GetBit(c, 2) || GetBit(c, 3)
						|| GetBit(a, 5) || GetBit(a, 6) || GetBit(b, 5) || GetBit(
						b, 6))
				|| (GetBit(a, 2) || GetBit(b, 2))
				&& !GetBit(c, 2)
				|| (GetBit(a, 3) || GetBit(b, 3))
				&& !GetBit(c, 3)
				|| (GetBit(a, 4) || GetBit(b, 4))
				&& !GetBit(c, 4)
				|| (GetBit(a, 5) || GetBit(b, 5))
				&& !GetBit(c, 5)
				&& !((GetBit(a, 1) || GetBit(b, 1)))
				|| (GetBit(a, 6) || GetBit(b, 6))
				&& !GetBit(c, 6)
				&& !((GetBit(a, 1) || GetBit(b, 1)))
				|| GetBit(c, 1)
				&& !(GetBit(a, 1) || GetBit(b, 1))
				|| GetBit(c, 2)
				&& !(GetBit(a, 1) || GetBit(b, 1) || GetBit(a, 2) || GetBit(b,
						2))
				|| GetBit(c, 3)
				&& !(GetBit(a, 1) || GetBit(b, 1) || GetBit(a, 3) || GetBit(b,
						3)) || GetBit(c, 4) && !(GetBit(a, 4) || GetBit(b, 4))
				|| GetBit(c, 5) && !(GetBit(a, 5) || GetBit(b, 5))
				|| GetBit(c, 6) && !(GetBit(a, 6) || GetBit(b, 6)))
			return -1;
		if (GetBit(c, 1)
				&& (GetBit(a, 5) || GetBit(a, 6) || GetBit(b, 5) || GetBit(b, 6)))
			ccc = 1;
		return ccc + GetBitCount(a & 0x0f) + GetBitCount(b & 0x0f)
				- GetBitCount(c & 0x0f);
	}

	private static int findDiffS(int a, int b, int c) {
		if (GetBit(a, 2) && !GetBit(c, 2) || GetBit(b, 3) && !GetBit(c, 3)
				|| GetBit(a, 5) || GetBit(a, 6) || GetBit(b, 5) || GetBit(b, 6)
				|| GetBit(c, 1) || GetBit(c, 2) && !GetBit(a, 2)
				|| GetBit(c, 3) && !GetBit(b, 3) || GetBit(c, 4)
				&& !(GetBit(a, 1) && GetBit(b, 1)) || (c == 0 && GetBit(b, 1))
			    || (c == 0 && GetBit(a, 1)))
			return -1;

		int s = 0;

		if (GetBit(a, 4))
			s = s + 1;
		if (GetBit(c, 5)) {
			if (GetBit(a, 4) && GetBit(b, 1)) {
				s = s - 1;
			} else
				return -1;
		}
		if (GetBit(b, 4))
			s = s + 1;

		if (GetBit(c, 6)) {
			if (GetBit(a, 1) && GetBit(b, 4)) {
				s = s - 1;
			} else
				return -1;
		}
		// Edge from a cover by edge from c or star in center
		if (GetBit(a, 1)
				&& !(GetBit(c, 2) || GetBit(c, 6) || GetBit(c, 4)
						&& GetBit(b, 1))
				|| GetBit(a, 3)
				|| GetBit(b, 1)
				&& !(GetBit(c, 3) || GetBit(c, 5) || GetBit(c, 4)
						&& GetBit(a, 1)) || GetBit(b, 2))
			s = s + 1;

		return (GetBitCount(a) + GetBitCount(b) - GetBitCount(c) - s);
	}
}
