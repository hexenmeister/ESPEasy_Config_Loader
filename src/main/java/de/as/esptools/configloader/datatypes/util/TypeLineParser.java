package de.as.esptools.configloader.datatypes.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeLineParser {

	public static class TypeDef {
		// suchen nach Muster: xxx[num]
		private static final Pattern WRAPPED_LINE_PATTERN = Pattern
				.compile("^\\s*(?:\\/\\*(.*)\\*\\/)?\\W*(?:(\\w+)(?:\\W*(\\w*)))*\\W*:(.*)$");

		// /* bla */ struct name { // comment
		// ALT:
		// ("^\\s*(?:\\/\\*(.*)\\*\\/)?\\W*(?:(\\w+)(?:\\W*(\\w*)))*\\W*\\{(.*)$")
		private static final Pattern STRUCT_LINE_PATTERN = Pattern
				.compile("^\\s*(?:\\/\\*(.*)\\*\\/)?\\W*(?:(\\w+)(?:\\W*(\\w*)))*\\W*\\W+(.*)\\{(.*)$");
		private String itemType;
		private String itemName;
		private String itemData;
		private String itemComment1;
		private String itemComment2;

		private List<TypeDef> children;

		private TypeDef() {
			this.children = new ArrayList<TypeDef>();
		}

		public static List<TypeDef> parse(StringTokenizerEx st) {
			List<TypeDef> ret = new ArrayList<TypeDef>();
			TypeDef inst;
			while ((inst = parseIntern(st)) != null) {
				ret.add(inst);
			}
			return ret;
		}

		protected static TypeDef parseIntern(StringTokenizerEx st) {
			String[] def;
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				if ((def = matchSimple(token)) != null) {
					TypeDef inst = new TypeDef();
					inst.itemComment1 = def[0];
					inst.itemType = def[1];
					inst.itemName = def[2];
					inst.itemData = def[3];
					inst.itemComment2 = def[4];
					while (st.hasMoreTokens()) {
						String nToken = st.previewToken();
						String[] nDef = matchSimple(nToken);
						if (isWrappedLine(nDef)) {
							st.nextToken();
							inst.merge(nDef);
						} else {
							return inst;
							// break;
						}
					}
					return inst;
				} else if ((def = matchStruct(token)) != null) {
					TypeDef inst = new TypeDef();
					inst.itemComment1 = def[0];
					inst.itemType = def[1];
					inst.itemName = def[2];
					inst.itemData = def[3];
					inst.itemComment2 = def[4];
					TypeDef instN;
					while ((instN = parseIntern(st)) != null) {
						inst.addChild(instN);
					}
					return inst;
				} else if (matchEndMark(token)) {
					return null;
				} else if (matchStopMark(token)) {
					return null;
				}
			}
			return null;
		}

		private static String[] matchStruct(String line) {
			// TODO
			Matcher matcher = STRUCT_LINE_PATTERN.matcher(line);
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

		private static String[] matchSimple(String line) {
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

		private static boolean matchEndMark(String line) {
			return line != null && line.trim().toUpperCase().startsWith("}");
		}

		private static boolean matchStopMark(String line) {
			return line != null && line.trim().toUpperCase().startsWith("#END");
		}

		private static String nn(String s) {
			if (s == null) {
				return "";
			}
			return s.trim();
		}

		public static boolean isWrappedLine(String[] def) {
			return /* itemType */ def[1] == null || /* itemType */ def[1].isEmpty();
		}

		public void merge(String[] inst) {
			this.itemData += " " + /* temData */ inst[3];
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

		public void addChild(TypeDef child) {
			this.children.add(child);
		}

		public List<TypeDef> getChildren() {
			return this.children;
		}

		@Override
		public String toString() {
			return "/* " + getItemComment1() + " */ " + getItemType() + " " + getItemName() + " : " + getItemData()
					+ " // " + getItemComment2() + " [children: " + this.getChildren().size() + "]";
		}

	}

	private StringTokenizerEx tokenizer;
	private TypeDef typeDef;
	// private boolean found = false;

	public TypeLineParser(String data) {
		if (data != null) {
			tokenizer = new StringTokenizerEx(data, "\n\r");
		} else {
			throw new IllegalArgumentException("Data string maynot be null");
		}

	}

	public boolean next() {
		while (this.tokenizer.hasMoreTokens()) {
			TypeDef def = TypeDef.parseIntern(this.tokenizer);
			if (def != null) {
				this.typeDef = def;
				return true;
			}
		}
		this.typeDef = null;
		return false;
	}

	private TypeDef getCurrent() {
		if (this.typeDef != null) {
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
