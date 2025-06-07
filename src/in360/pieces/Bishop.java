package in360.pieces;
import in360.Piece;

public class Bishop extends Piece{

    private final int BISHOP_WHITE = 0x2657;
	private final int BISHOP_BLACK = 0x265D;


    public Bishop(int x, int y, ColorP color) {
        this.x = x;
        this.y = y;
        if (color == ColorP.BLACK){
            this.piece_int = BISHOP_BLACK;
            this.image="assets/bishopB.png";
        }
        else{
            this.piece_int = BISHOP_WHITE;
            this.image="assets/bishopW.png";
        }
        
    }






    
    public int getBISHOP_WHITE() {
        return BISHOP_WHITE;
    }
    public int getBISHOP_BLACK() {
        return BISHOP_BLACK;
    }

    
}
