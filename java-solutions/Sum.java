public class Sum {
	public static void main(String[] args) {
		int sum = 0;
		for(int i = 0; i < args.length; i++) {
			int indexStart = -1, indexEnd = -1;
			for (int j = 0; j < args[i].length(); j++) {
				if ( ((int) args[i].charAt(j) >= (int) '0' && (int) args[i].charAt(j) <= (int) '9')
				 || ((int) args[i].charAt(j) == (int) '+') || ((int) args[i].charAt(j) == (int) '-') ) {
					if (indexStart == -1) {
						indexStart = j;
					}
					indexEnd = j;
				}
				else if (indexStart != -1) {
					sum += Integer.parseInt(args[i].substring(indexStart,indexEnd + 1));
					indexStart = -1;
				}
			}
			if (indexStart != -1) {
				sum += Integer.parseInt(args[i].substring(indexStart,indexEnd + 1));
			}
		}
		System.out.println(sum);
	}
}