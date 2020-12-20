package com.example.androidchess43;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.example.androidchess43.Pieces.Bishop;
import com.example.androidchess43.Pieces.King;
import com.example.androidchess43.Pieces.Knight;
import com.example.androidchess43.Pieces.Pawn;
import com.example.androidchess43.Pieces.Piece;
import com.example.androidchess43.Pieces.Queen;
import com.example.androidchess43.Pieces.Rook;
import com.example.androidchess43.Pieces.Square;
import com.example.androidchess43.data.model.Game;
import com.example.androidchess43.util.AppData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RecordGame extends AppCompatActivity implements EndRecordGame.EndRecordGameListener{

    Square[][] squares = new Square[8][8];
    TableRow[] board;
    TableLayout table;
    TextView title;
    HashMap<TableRow, Integer> mapRowsToIndex = new HashMap<>();
    int[] currentMove = {-1,-1,-1,-1}; // startRow, startCol, endRow, endCol
    Boolean turn = true;
    TextView turnDesc;
    String promotionPiece;
    ArrayList<int[]> moveList = new ArrayList<>();
    ArrayList<Integer> successList = new ArrayList<>();
    ArrayList<String> promoList = new ArrayList<>();
    Game selectedGame = new Game(null,null,null,null);
    int moveIndex = 0;
    int successIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_game);
        table = findViewById(R.id.BoardLayoutRec);
        turnDesc = findViewById(R.id.turnTextView);
        initializeBoard();
        //entryBoard();
        initializeEmptyBoard();
        initializeMap();
        //printBoard(squares);
        translateSquaresToBoard();
//        TableRow r = (TableRow) table.getChildAt(0);
//        ImageView i = new ImageView(this);
//        i.setClickable(true);
//        i.setFocusable(true);
//        i.setImageResource(R.drawable.bishop);
//        i.setBackgroundColor(getResources().getColor(R.color.tan, null));
//        r.removeViewAt(0);
//        r.addView(i,0, new TableRow.LayoutParams( 49, TableRow.LayoutParams.MATCH_PARENT));
//        r.getChildAt(0).setOnClickListener(v -> handleClick(v));
//        r.removeViewAt(1);
//        ImageView i1 = new ImageView(this);
//        i1.setColorFilter(R.color.white);
//        i1.setClickable(true);
//        i1.setFocusable(true);
//        i1.setImageResource(R.drawable.bishop);
//        i1.setBackgroundColor(getResources().getColor(R.color.black, null));
//        r.addView(i1,1, new TableRow.LayoutParams( 49, TableRow.LayoutParams.MATCH_PARENT));
        selectedGame = (Game) getIntent().getSerializableExtra("selectedGame");
        moveList = selectedGame.getMoves();
        successList = selectedGame.getSuccess();
        promoList = selectedGame.getPromotions();
        Log.d("debug", selectedGame.getPromotions().toString());
        title = findViewById(R.id.titleView);
        title.setText(selectedGame.getName());
        Button next = findViewById(R.id.next);
        next.setOnClickListener(v -> handleNext());


    }
    public void handleNext(){
        Button next = findViewById(R.id.next);
        String extra = "";
        turn = !turn;
        if (turn){
            turnDesc.setText("White's Move"+extra);
        }
        else{
            turnDesc.setText("Black's Move"+extra);
        }
        if(moveIndex == selectedGame.getMoves().size()){
            next.setEnabled(false);
            DialogFragment newFragment = new EndRecordGame();
            Bundle bundle = new Bundle();
            if (turn){
                bundle.putString(GameOver.WINNER_KEY,
                        "White wins!");
            }
            else{
                bundle.putString(GameOver.WINNER_KEY,
                        "Black wins!");
            }
            newFragment.setArguments(bundle);
            newFragment.show(getSupportFragmentManager(), "EndRecordGame");
        }else{
            int[] move = selectedGame.getMoves().get(moveIndex);
            int success = selectedGame.getSuccess().get(successIndex);
            if(move[0]==-2&&move[1]==-2&&move[2]==-2&&move[3]==-2){
                next.setEnabled(false);
                DialogFragment newFragment = new EndRecordGame();
                Bundle bundle = new Bundle();
                bundle.putString(GameOver.WINNER_KEY, "Draw!");
                newFragment.setArguments(bundle);
                newFragment.show(getSupportFragmentManager(), "EndRecordGame");
            }else if(move[0]==-3&&move[1]==-3&&move[2]==-3&&move[3]==-3) {
                next.setEnabled(false);
                String ch = selectedGame.getPromotions().get(moveIndex);
                DialogFragment newFragment = new EndRecordGame();
                Bundle bundle = new Bundle();
                if (ch.equals("W")){
                    bundle.putString(GameOver.WINNER_KEY,
                            "White wins!");
                }
                else{
                    bundle.putString(GameOver.WINNER_KEY,
                            "Black wins!");
                }
                newFragment.setArguments(bundle);
                newFragment.show(getSupportFragmentManager(), "EndRecordGame");
            }else{
                makeMove(squares,success,move);
                moveIndex++;
                successIndex++;
            }

        }



       // makeMove(squares,successList.get(successIndex),moveList.get(moveIndex));
    }
    public void goToViewGames(){
        Intent intent = new Intent(this, ViewGames.class);
        startActivity(intent);
    }
    public void initializeMap(){
        for (int x=0; x< board.length; x++){
            mapRowsToIndex.put(board[x], x);
        }
    }
    public Boolean getSquareColor(int row, int col){
        if ((row % 2 == 0 && col % 2 != 0) || (row % 2 != 0 && col % 2 == 0)){
            return false;
        }
        else {
            return true;
        }
    }
    public void printBoard(Square [] [] board) {

        for (int x=0; x< 8; x++) { // row
            String row = "";
            for (int y=0; y< 8; y++) {
                row += board[x][y].getName() + " ";
            }
            Log.d("board", row);
        }
        System.out.println();
        //System.out.println(" a  b  c  d  e  f  g  h");
        //System.out.println(" 0  1  2  3  4  5  6  7");
    }
    public void translateSquaresToBoard(){
        for (int x=0; x < squares.length; x++){
            for (int y=0; y < squares.length; y++){
                Square s = squares[x][y];
                TableRow r = board[x];
                View v = new TextView(this);
                if (s instanceof Piece){
                    Piece p = (Piece) s;
                    ImageView i = new ImageView(this);
                    i.setImageResource(p.getResourceImage());
                    v = i;
                }
                v.setClickable(false);
                v.setFocusable(true);
                Boolean squareColor = getSquareColor(x,y);
                if (squareColor) {
                    v.setBackgroundColor(getResources().getColor(R.color.light, null));
                }
                else{
                    v.setBackgroundColor(getResources().getColor(R.color.dark, null));
                }
                r.removeViewAt(y);
                float width = getResources().getDimension(R.dimen.chart_width);
                r.addView(v,y, new TableRow.LayoutParams((int) width, (int) width));
                //r.getChildAt(y).setOnClickListener(c -> handleClick(c));
            }
        }
    }
    public void initializeBoard(){
        //initialize white pieces
        squares[0][0] = new Rook(false);
        squares[0][1] = new Knight(false);
        squares[0][2] = new Bishop(false);
        squares[0][3] = new Queen(false);
        squares[0][4] = new King(false);
        squares[0][5] = new Bishop(false);
        squares[0][6] = new Knight(false);
        squares[0][7] = new Rook(false);
        for (int x=0; x<8; x++) {
            squares[1][x] = new Pawn(false);
        }
        //initialize blank squares
        for(int row = 2; row < 6; row++) {

            for(int col = 0; col < 8; col++) {

                if ((row % 2 == 0 && col % 2 != 0) || (row % 2 != 0 && col % 2 == 0)){
                    squares[row][col] = new Square(false);
                }
                else {
                    squares[row][col] = new Square(true);
                }
            }
        }
        //initialize black pieces
        squares[7][0] = new Rook(true);
        squares[7][1] = new Knight(true);
        squares[7][2] = new Bishop(true);
        squares[7][3] = new Queen(true);
        squares[7][4] = new King(true);
        squares[7][5] = new Bishop(true);
        squares[7][6] = new Knight(true);
        squares[7][7] = new Rook(true);
        for (int x=0; x<8; x++) {
            squares[6][x] = new Pawn(true);
        }
    }

    public void initializeEmptyBoard(){
        board = new TableRow[table.getChildCount()];
        for (int x=0; x < table.getChildCount(); x++){
            if (table.getChildAt(x) instanceof TableRow){
                TableRow r = (TableRow) table.getChildAt(x);
                board[x] = r;

            }
        }
    }
    public int [] getIndexes(View v){
        TableRow r = (TableRow) v.getParent();
        int row = mapRowsToIndex.get(r);
        int col = -1;
        for (int x=0; x < r.getChildCount(); x++){
            if (v.equals(r.getChildAt(x))){
                col = x;
            }
        }
        return new int[]{row, col};
    }
    public void visuallySelect(View v, int [] indexes){
        // handle visually selecting
        if (v.getAlpha() == 0.7f){ // selected to not selected
            Log.d("debug", "already clicked");
            if (getSquareColor(indexes[0], indexes[1])) {
                v.setBackgroundColor(getResources().getColor(R.color.light, null));
            }
            else{
                v.setBackgroundColor(getResources().getColor(R.color.dark, null));
            }
            v.setAlpha((float) 1.0);
        }
        else{ // not selected to selected
            v.setAlpha((float) 0.7);
            v.setBackgroundColor(getResources().getColor(R.color.green, null));
            Log.d("debug", String.valueOf(v.getParent()));
        }
    }
    public void clearBoardSelection(){
        for (int x=0; x < board.length; x++){
            for (int y=0; y <8; y++){
                TableRow r = board[x];
                if (getSquareColor(x, y)) {
                    r.getChildAt(y).setAlpha(1.0f);
                    r.getChildAt(y).setBackgroundColor(getResources().getColor(R.color.light, null));
                }
                else{
                    r.getChildAt(y).setAlpha(1.0f);
                    r.getChildAt(y).setBackgroundColor(getResources().getColor(R.color.dark, null));
                }
            }
        }
        currentMove = new int[]{-1, -1, -1, -1};
    }
    public void illegalMoveToast(){
        Toast.makeText(this, "Illegal Move", Toast.LENGTH_LONG).show();
        clearBoardSelection();
    }
    public Square [] [] copyArray(Square [] [] arr){
        Square [] [] newArray = new Square [arr.length][arr.length];
        for(int i = 0; i < arr.length; i++) {
            for(int s = 0; s < arr.length; s++) {
                if (arr[i][s] instanceof Piece) {
                    if (arr[i][s] instanceof Pawn) {
                        newArray[i][s] = new Pawn(arr[i][s].getColor());
                    }
                    else if (arr[i][s] instanceof Queen) {
                        newArray[i][s] = new Queen(arr[i][s].getColor());
                    }
                    else if (arr[i][s] instanceof King) {
                        newArray[i][s] = new King(arr[i][s].getColor());
                    }
                    else if (arr[i][s] instanceof Knight) {
                        newArray[i][s] = new Knight(arr[i][s].getColor());
                    }
                    else if (arr[i][s] instanceof Rook) {
                        newArray[i][s] = new Rook(arr[i][s].getColor());
                    }
                    else if (arr[i][s] instanceof Bishop) {
                        newArray[i][s] = new Bishop(arr[i][s].getColor());
                    }
                }
                else {
                    newArray[i][s] = new Square(arr[i][s].getColor());
                }
            }
        }
        return newArray;
    }
    public boolean putYourselfInCheck(Square[] [] board) {

        int [] kingPos = findKing(board, turn);
        //System.out.println("My King Position: "+ kingPos[0]+ " " + kingPos[1]);

        for (int x=0; x<board.length; x++) {
            for (int y=0; y<board.length; y++) {
                if (board[x][y] instanceof Piece && board[x][y].getColor() != turn) { // if it is a piece and opposing piece
                    Piece p = (Piece) board[x][y];
                    int [] move = {x,y,kingPos[0], kingPos[1]};
                    int success = p.move(board,move,!turn);
                    //System.out.println("Checking:  " + p.getName() + " " + success);
                    if (success != -1) {
                        return true; // there is a check
                    }
                }
            }
        }
        return false;
    }
    public Square underlyingColor(int row, int col) {

        if ((row % 2 == 0 && col % 2 != 0) || (row % 2 != 0 && col % 2 == 0)){
            return new Square(false);
        }
        else {
            return new Square(true);
        }
    }
    public Square getPromotionPiece(String ch,Boolean color) {
        Square s;
        switch(ch) {
            case "R":
                s = new Rook(color);
                break;
            case "N":
                s = new Knight(color);
                break;
            case "B":
                s = new Bishop(color);
                break;
            default:
                s = new Queen(color);
        }
        return s;
    }
    /**
     * Method for actually performing the valid, inputed move.
     * @param boardArray Square[][] board.
     * @param success value corresponding to the return value of move in the Piece sub-classes.
     * @param move int[] containing the move to be made in [rowStart colStart rowEnd colEnd] format.
     * */
    public void makeMove(Square[] [] boardArray, int success, int [] move) {

        int rowStart = move[0];
        int colStart = move[1];
        int rowEnd = move[2];
        int colEnd = move[3];
        if (success == 2) { // promotion
            String ch = promoList.get(moveIndex);
            boardArray[rowEnd][colEnd] = getPromotionPiece(ch,!turn);
            boardArray[rowStart][colStart] = underlyingColor(rowStart,colStart);
        }else if (success == 4) {
            boardArray[rowEnd][colEnd] = boardArray[rowStart][colStart];
            boardArray[rowStart][colStart] = underlyingColor(rowStart,colStart);
            boardArray[rowEnd][colEnd-1] = boardArray[rowEnd][colEnd+1];
            boardArray[rowEnd][colEnd+1] = underlyingColor(rowEnd,colEnd-1);
        }else if (success == 5) {
            boardArray[rowEnd][colEnd] = boardArray[rowStart][colStart];
            boardArray[rowStart][colStart] = underlyingColor(rowStart,colStart);
            boardArray[rowEnd][colEnd+1] = boardArray[rowEnd][colEnd-2];
            boardArray[rowEnd][colEnd-1] = underlyingColor(rowEnd,colEnd+1);
        }
        else {
            if (success == 3) { // enpassant
                if (!turn) { boardArray[rowEnd-1][colEnd] = this.underlyingColor(rowEnd-1, colEnd);}
                else { boardArray[rowEnd+1][colEnd] = this.underlyingColor(rowEnd+1, colEnd);}
            }
            boardArray[rowEnd][colEnd] = boardArray[rowStart][colStart];
            boardArray[rowStart][colStart] = underlyingColor(rowStart,colStart);
        }
        translateSquaresToBoard();
        clearBoardSelection();
    }
    public void handleMove(){
        Piece p = (Piece) squares[currentMove[0]][currentMove[1]];
        int success = p.move(squares, currentMove, turn);
        if (success == -1) {
            illegalMoveToast();
            return;
        }
        else {
            // copy the board and pretend to move piece
            Square [] [] tempBoard = copyArray(squares);
            makeMove(tempBoard, success, currentMove);
            // call a function to determine a check on yourself
            Boolean ownCheck = putYourselfInCheck(tempBoard);
            //move piece
            if (ownCheck) { illegalMoveToast(); return; }
            makeMove(squares, success, currentMove);
            // call function find either check or checkmate or nothing
            Boolean c = Check(squares, turn);
            String extra = "";
            if (c) {
                //printBoard(squares);
                Boolean cm = CheckMate();
                if (!cm) {System.out.println(); extra+=": check"; Toast.makeText(this, "Check", Toast.LENGTH_LONG).show();}
                else {
                    //printBoard(squares);
                    Log.d("debug","checkmate");
                    DialogFragment newFragment = new GameOver();
                    Bundle bundle = new Bundle();
                    if (turn){
                        bundle.putString(GameOver.WINNER_KEY,
                                "White wins!");
                    }
                    else{
                        bundle.putString(GameOver.WINNER_KEY,
                                "Black wins!");
                    }
                    newFragment.setArguments(bundle);
                    newFragment.show(getSupportFragmentManager(), "gameover");
                }
            }
            translateSquaresToBoard();
            clearBoardSelection();
            //turn = !turn;
            if (turn){
                turnDesc.setText("White's Move"+extra);
            }
            else{
                turnDesc.setText("Black's Move"+extra);
            }
        }
    }
    /*public void handleClick(View v){
        Log.d("debug", "clicked");
        int[] indexes = getIndexes(v);
        Square s = squares[indexes[0]][indexes[1]];
        if (currentMove[0] == -1){ // first selection
            //Log.d("debug", "first selection");
            //check if there is a piece
            if (!(s instanceof Piece)){
                Toast.makeText(this, "Invalid. Select a square with your piece.", Toast.LENGTH_LONG).show();
                return;
            }
            // check if piece is yours
            if (s.getColor() != turn){
                Toast.makeText(this, "Invalid. Select your own piece.", Toast.LENGTH_LONG).show();
                return;
            }
            currentMove[0] = indexes[0];
            currentMove[1] = indexes[1];
            visuallySelect(v, indexes);
        }
        else if (currentMove[2] == -1){  // second selection
            // Log.d("debug", "second selection");
            // moving to the same place
            visuallySelect(v,indexes);
            if (currentMove[0] == indexes[0] && currentMove[1] == indexes[1]){
                illegalMoveToast();
                return;
            }
            // check if there is a piece that is not yours
            if ((s instanceof Piece) && s.getColor() == turn){
                illegalMoveToast();
                return;
            }
            currentMove[2] = indexes[0];
            currentMove[3] = indexes[1];
            Piece p = (Piece) squares[currentMove[0]][currentMove[1]];
            if ((turn && currentMove[2] == 0 && p instanceof Pawn ) ||  (!turn && currentMove[2] == 7 && p instanceof Pawn)){
                Log.d("debug", "promotion");
                DialogFragment newFragment = new PromotionDialog();
                newFragment.show(getSupportFragmentManager(), "promotion");
            }
            else{
                handleMove();
            }
        }

    } // end of handle click

     */
    public int [] findKing(Square [] [] board, Boolean color) {

        for (int x=0; x < board.length; x++) {
            for (int y=0; y < board.length; y++) {
                if (board[x][y] instanceof Piece && board[x][y] instanceof King && board[x][y].getColor() == color) {
                    int [] pos = {x,y};
                    return pos;
                }
            }
        }
        return null;
    }
    public boolean Check(Square[][] board, Boolean color) {

        int[] moves = {0,0,0,0};
        //get position of the enemy king
        int [] kingPos = findKing(board, !color);
        //System.out.println("My King is at: " + kingPos[0] + " " + kingPos[1]);
        moves[2] = kingPos[0];
        moves[3] = kingPos[1];
        //Find every piece
        for(int i = 0; i<board.length; i++) {
            for(int j=0; j<board.length; j++) {
                if (board[i][j] instanceof Piece && board[i][j].getColor() == color) {
                    Piece piece = (Piece) board[i][j];
                    moves[0] = i;
                    moves[1] = j;
                    int success = piece.move(board, moves, color);
                    //System.out.println("Checking:  " + piece.getName() + " " + success);
                    if (success!=-1){
                        //System.out.println(piece.getName()+ " checking king");
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     * Method for determining checkmate. Makes a copy of the board, and moves makes every possible move that a player could make while
     * in check. If there is no move which can take a player out of check, then it is a checkmate.
     * @return true if there is a checkmate, false otherwise.
     */
    public boolean CheckMate() {
        Square [] [] board = copyArray(squares);
        // check if opposing pieces can move to block the check
        for (int x=0; x<squares.length; x++) {
            for (int y=0; y<squares.length; y++) {
                if (board[x][y] instanceof Piece && board[x][y].getColor() != turn) { // if it is a piece and opposing piece
                    Piece p = (Piece) board[x][y];
                    for (int i=0; i<board.length; i++) {
                        for (int j=0; j<board.length; j++) {
                            int [] move = {x,y,i,j};
                            int success = p.move(board,move,!turn);
                            if (success != -1) {
                                makeMove(board,success, move);
                                Boolean c = Check(board, turn);
                                if (!c) {
                                    return false;
                                }
                                board = copyArray(squares);
                            }

                        }
                    }
                }
            }
        }

        return true; // checkmate
    }
    public void entryBoard() {

        for(int row = 0; row < 8; row++) {

            for(int col = 0; col < 8; col++) {

                if ((row % 2 == 0 && col % 2 != 0) || (row % 2 != 0 && col % 2 == 0)){
                    squares[row][col] = new Square(false);
                }
                else {
                    squares[row][col] = new Square(true);
                }
            }
        }

        squares[0][0] = new King(false);
        squares[1][0] = new Pawn(true);
        squares[0][7] = new Rook(true);
        squares[7][7] = new Bishop(true);
        squares[7][0] = new Rook(true);
        squares[5][2] = new Queen(false);
        squares[7][2] = new King(true);
        squares[6][2] = new Pawn(true);
        squares[1][1] = new Pawn(true);
        squares[6][5] = new Rook(false);


    }

    public void select(DialogFragment dialog) {
        String name = ((PromotionDialog)dialog).getPiece();
        promotionPiece = name;
        handleMove();
    }
    @Override
    public void exitGame(DialogFragment dialog) {
        goToHome();
    }

    public void goToHome(){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}
