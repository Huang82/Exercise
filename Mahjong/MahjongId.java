public enum MahjongId {
    /* a:萬 b:筒 c:條 */
    one("a1", "b1", "c1"),
    two("a2", "b2", "c2"),
    three("a3", "b3", "c3"),
    four("a4", "b4", "c4"),
    five("a5", "b5", "c5"),
    six("a6", "b6", "c6"),
    seven("a7", "b7", "c7"),
    eight("a8", "b8", "c8"),
    nine("a9", "b9", "c9");

    public String a;
    public String b;
    public String c;

    MahjongId(String a, String b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

}
