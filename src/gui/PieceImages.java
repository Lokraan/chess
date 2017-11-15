package gui;

import pieces.*;

public class PieceImages {

	public static String getImagePath(Piece piece) {
		String path = "src/img/";
		if(piece == null) return null;
		switch(piece.getColor()) {
			case White: path += "w"; break;
			default: path += "b"; break;
		}
		if(piece instanceof Pawn) path += "Pawn.png";
		else if(piece instanceof Rook) path += "Rook.png";
		else if(piece instanceof Knight) path += "Knight.png";
		else if(piece instanceof Bishop) path += "Bishop.png";
		else if(piece instanceof King) path += "King.png";
		else if(piece instanceof Queen) path += "Queen.png";
		
		if(path == "img/w" || path == "img/b") return null;
		
		return path;
	}
	
}
