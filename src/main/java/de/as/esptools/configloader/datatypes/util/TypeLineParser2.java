package de.as.esptools.configloader.datatypes.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeLineParser2 {

	public static class TypeDef {
		// suchen nach Muster: xxx[num]
		private static final Pattern WRAPPED_LINE_PATTERN = Pattern
				.compile("^\\s*(?:\\/\\*(.*)\\*\\/)?\\W*(?:(\\w+)(?:\\W*(\\w*)))*\\W*:(.*)$");
		
		// /* bla */ struct name { // comment
		private static final Pattern STRUCT_LINE_PATTERN = Pattern
				.compile("^\\s*(?:\\/\\*(.*)\\*\\/)?\\W*(?:(\\w+)(?:\\W*(\\w*)))*\\W*\\{(.*)$");
		
		private String itemType;
		private String itemName;
		private String itemData;
		private String itemComment1;
		private String itemComment2;

		private List<TypeDef> children;

		private TypeDef(StringTokenizerEx st) {
			this.children = new ArrayList<TypeDef>();
			this.parseIntern(st);
		}

		public static TypeDef parse(StringTokenizerEx st) {
			TypeDef inst = new TypeDef(st);
			return inst;
		}

		private void parseIntern(StringTokenizerEx st) {
			String token = st.nextToken();
			String[] def;
			if ((def = this.matchSimple(token)) != null) {
				this.itemComment1 = def[0];
				this.itemType = def[1];
				this.itemName = def[2];
				this.itemData = def[3];
				this.itemComment2 = def[4];
				while (st.hasMoreTokens()) {
					String nToken = st.previewToken();
					String[] nDef = this.matchSimple(nToken);
					if (this.isWrappedLine(nDef)) {
						st.nextToken();
						this.merge(nDef);
					} else {
						break;
					}
				}
			} else if (this.matchStruct(token)) {
				// TODO

			}
		}

		private boolean matchStruct(String line) {
			// TODO

			return false;
		}

		public String getItemType() {
			return itemType;
		}

		public String getItemName() {
			return itemName;
		}

		public String getItemData() {
			return itemData;
		}

		public String getItemComment1() {
			return itemComment1;
		}

		public String getItemComment2() {
			return itemComment2;
		}

		@Override
		public String toString() {
			return "/* " + getItemComment1() + " */ " + getItemType() + " " + getItemName() + " : " + getItemData()
					+ " // " + getItemComment2();
		}

		private String[] matchSimple(String line) {
			Matcher matcher = WRAPPED_LINE_PATTERN.matcher(line);
			String[] ret = new String[5];
			if (matcher.find()) {
				/* itemComment1 */ ret[0] = nn(matcher.group(1));
				/* itemType */ ret[1] = nn(matcher.group(2));
				/* itemName */ ret[2] = nn(matcher.group(3));
				/* itemData */ ret[3] = nn(matcher.group(4));
				int pos;
				if ((pos = ret[3].indexOf("//")) >= 0) {
					/* itemComment2 */ ret[4] = /* itemData */ ret[3].substring(pos + 2).trim();
					/* itemData */ ret[3] = /* itemData */ ret[3].substring(0, pos).trim();
				} else {
					/* itemComment2 */ ret[4] = "";
				}
				return ret;
			} else {
				return null;
			}
		}

		private static String nn(String s) {
			if (s == null) {
				return "";
			}
			return s.trim();
		}

		public boolean isWrappedLine(String[] def) {
			return /* itemType */ def[1] == null || /* itemType */ def[1].isEmpty();
		}

		public void merge(String[] inst) {
			this.itemData += " " + /* temData */ inst[3];
		}
	}

	private StringTokenizerEx tokenizer;
	private TypeDef typeDef;
	private boolean found = false;

	public TypeLineParser2(String data) {
		if (data != null) {
			tokenizer = new StringTokenizerEx(data, "\n\r");
		} else {
			throw new IllegalArgumentException("Data string maynot be null");
		}

	}

	public boolean next() {
		while (this.tokenizer.hasMoreTokens()) {
			TypeDef def = new TypeDef(this.tokenizer);
			if (def != null) {
				this.typeDef = def;
				return true;
			}
		}

		// String token = this.tokenizer.nextToken();
		// TypeDef def = TypeDef.match(token);
		// if (def != null) {
		// while (this.tokenizer.hasMoreTokens()) {
		// String nToken = this.tokenizer.previewToken();
		// TypeDef nDef = TypeDef.match(nToken);
		// if (nDef.isWrappedLine()) {
		// this.tokenizer.nextToken();
		// def.merge(nDef);
		// } else {
		// break;
		// }
		// }
		// this.typeDef = def;
		// this.found = true;
		// return true;
		// // break;
		// }
		// }
		// this.found = false;
		return false;
	}

	private TypeDef getCurrent() {
		if (this.found) {
			return this.typeDef;
		}
		throw new IndexOutOfBoundsException();
	}

	public String getItemType() {
		return this.getCurrent().getItemType();
	}

	public String getItemName() {
		return this.getCurrent().getItemName();
	}

	public String getItemData() {
		return this.getCurrent().getItemData();
	}

	public String getItemComment1() {
		return this.getCurrent().getItemComment1();
	}

	public String getItemComment2() {
		return this.getCurrent().getItemComment2();
	}

}
