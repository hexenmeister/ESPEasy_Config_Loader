package de.as.esptools.configloader.datatypes.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeLineParser {

//	public static void main(String[] a) {
//		// Pattern p =
//		// Pattern.compile("^\\s*(?:\\/\\*(.*)\\*\\/)?\\W*(?:(\\w+)(?:\\W*(\\w*)))*\\s*:(.*)$");
//		Pattern p = Pattern.compile("^\\s*(?:\\/\\*(.*)\\*\\/)?\\W*(?:(\\w+)(?:\\W*(\\w*)))*\\W*:(.*)$");
//		Matcher m = p.matcher("/* comment 1   */ type name : data  ww // comment2");
//		if (m.find()) {
//			for (int i = 0, n = m.groupCount(); i <= n; i++) {
//				System.out.println(i + ":" + m.group(i));
//			}
//		}
//	}

//	private List<TypeDef> items = new ArrayList<TypeDef>();
//	private int pos = -1;

	public static class TypeDef {
		// suchen nach Muster: xxx[num]
		private static final Pattern WRAPPED_LINE_PATTERN = Pattern
				.compile("^\\s*(?:\\/\\*(.*)\\*\\/)?\\W*(?:(\\w+)(?:\\W*(\\w*)))*\\W*:(.*)$");
		private String itemType;
		private String itemName;
		private String itemData;
		private String itemComment1;
		private String itemComment2;

		private TypeDef() {
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
			return "/* " + getItemComment1() + " */ " + getItemType() + " " + getItemName() + " : " + getItemData() + " // " + getItemComment2();
		}

		public static final TypeDef match(String line) {
			Matcher matcher = WRAPPED_LINE_PATTERN.matcher(line);
			if (matcher.find()) {
				TypeDef inst = new TypeDef();
				inst.itemComment1 = nn(matcher.group(1));
				inst.itemType = nn(matcher.group(2));
				inst.itemName = nn(matcher.group(3));
				inst.itemData = nn(matcher.group(4));
				int pos;
				if ((pos = inst.itemData.indexOf("//")) >= 0) {
					inst.itemComment2 = inst.itemData.substring(pos + 2).trim();
					inst.itemData = inst.itemData.substring(0, pos).trim();
				} else {
					inst.itemComment2 = "";
				}
				return inst;
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

		public boolean isWrappedLine() {
			return this.getItemType() == null || this.getItemType().isEmpty();
		}

		public void merge(TypeDef inst) {
			this.itemData += " " + inst.getItemData();
		}
	}

	private StringTokenizerEx tokenizer;
	private TypeDef typeDef;
	private boolean found=false;
	
	public TypeLineParser(String data) {
		if (data != null) {
			tokenizer = new StringTokenizerEx(data, "\n\r");
//			while (st.hasMoreTokens()) {
//				String token = st.nextToken();
//				TypeDef def = TypeDef.match(token);
//				if (def != null) {
//					if (def.isWrappedLine()) {
//						if (this.items.size() > 0) {
//							this.items.get(this.items.size() - 1).merge(def);
//						} else {
//							throw new IndexOutOfBoundsException("no previous element");
//						}
//					} else {
//						this.items.add(def);
//					}
//				}
//			}
		} else {
			throw new IllegalArgumentException("Data string maynot be null");
		}

	}

	public boolean next() {
		while(this.tokenizer.hasMoreTokens()) {
			String token = this.tokenizer.nextToken();
			TypeDef def = TypeDef.match(token);
			if (def != null) {
				while(this.tokenizer.hasMoreTokens()) {
					String nToken = this.tokenizer.previewToken();
					TypeDef nDef = TypeDef.match(nToken);
					if (nDef.isWrappedLine()) {
						this.tokenizer.nextToken();
						def.merge(nDef);
					} else {
						break;
					}
				}
				this.typeDef=def;
				this.found=true;
				return true;
//				break;
			} 
		}
//		if (pos < this.items.size() - 1) {
//			pos++;
//			return true;
//		}
		this.found=false;
		return false;
	}

	private TypeDef getCurrent() {
		if(this.found) {
			return this.typeDef;
		}
		throw new IndexOutOfBoundsException();

		//		if (this.pos >= this.items.size()) {
//			throw new IndexOutOfBoundsException("" + this.pos);
//		}
//		return this.items.get(this.pos);
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

//	public int getItemCount() {
//		return this.items.size();
//	}

//	@Override
//	public String toString() {
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0, n = this.items.size(); i < n; i++) {
//			sb.append(this.items.get(i));
//			sb.append("\r\n");
//		}
//		return sb.toString();
//	}
}
