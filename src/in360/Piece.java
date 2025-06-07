package in360;

public class Piece {

    protected int x;
    protected int y;
    public enum ColorP { WHITE, BLACK }
    protected ColorP color;
    protected int piece_int;
    protected String image;


    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public ColorP getColor() {
        return color;
    }
    public void setColor(ColorP color) {
        this.color = color;
    }
    public int getPiece_int() {
        return piece_int;
    }
    public void setPiece_int(int piece_int) {
        this.piece_int = piece_int;
    }
  
}
