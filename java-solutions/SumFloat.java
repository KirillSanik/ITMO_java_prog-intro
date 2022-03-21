public class SumFloat {
	public static void main(String[] args) {
		float sum = 0;
		for(int i = 0; i < args.length; i++) {
			int indexStart = -1, indexEnd = -1;
			for (int j = 0; j < args[i].length(); j++) {
				if ( !Character.isWhitespace(args[i].charAt(j)) ) {
					if (indexStart == -1) {
						indexStart = j;
					}
					indexEnd = j;
				}
				else if (indexStart != -1) {
					sum += Float.parseFloat(args[i].substring(indexStart,indexEnd + 1));
					indexStart = -1;
				}
			}
			if (indexStart != -1) {
				sum += Float.parseFloat(args[i].substring(indexStart,indexEnd + 1));
			}
		}
		System.out.println(sum);
	}
}