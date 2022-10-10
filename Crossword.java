import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public final class Crossword implements WordPuzzleInterface {

	/*
	 * fills out a word puzzle defined by an empty board.
	 * The characters in the empty board can be:
	 *    '+': any letter can go here
	 *    '-': no letter is allowed to go here
	 *     a letter: this letter has to remain in the filled puzzle
	 *  @param board is a 2-d array representing the empty board to be filled
	 *  @param dictionary is the dictinary to be used for filling out the puzzle
	 *  @return a 2-d array representing the filled out puzzle
	 */

    public char[][] fillPuzzle(char[][] board, DictInterface dictionary){
		StringBuilder[] rowStr = new StringBuilder[board.length];
		StringBuilder[] colStr = new StringBuilder[board[0].length];
		for(int i = 0; i < rowStr.length; i++){
			rowStr[i] = new StringBuilder();
		}
		for(int i = 0; i < colStr.length; i++){
			colStr[i] = new StringBuilder();
		}

		char[][] result = new char[board.length][board[0].length];

		return recursiveMethod(board, result, dictionary, rowStr, colStr, -1, board[0].length - 1);
	}

	public char[][] recursiveMethod(char[][] emptyBoard, char[][] filledBoard, DictInterface dictionary,
									StringBuilder[] rowStr, StringBuilder[] colStr, int row, int col){
		if(row == emptyBoard.length - 1 && col == emptyBoard[0].length - 1)
			return filledBoard;
		else if(col == emptyBoard[0].length - 1){
			row++;
			col = 0;
		}else{
			col++;
		}
		char character = emptyBoard[row][col];
		if(character == '+'){
			for(char cur = 'a'; cur <= 'z'; cur++){
				rowStr[row].append(cur);
				colStr[col].append(cur);
				boolean isRowPrefix = isPrefix(rowStr[row], rowStr[row].lastIndexOf("-") + 1, rowStr[row].length() - 1, dictionary);
				boolean isColPrefix = isPrefix(colStr[col], colStr[col].lastIndexOf("-") + 1, colStr[col].length() - 1, dictionary);
				boolean isRowWord = isWord(rowStr[row], rowStr[row].lastIndexOf("-") + 1, rowStr[row].length() - 1, dictionary);
				boolean isColWord = isWord(colStr[col], colStr[col].lastIndexOf("-") + 1, colStr[col].length() - 1, dictionary);
				boolean isRowEnd = (col == emptyBoard[0].length - 1) || (emptyBoard[row][col + 1] == '-');
				boolean isColEnd = (row == emptyBoard.length - 1) || (emptyBoard[row + 1][col] == '-');
				if ((!isRowEnd && !isRowPrefix)
						|| (isRowEnd && !isRowWord)
						|| (!isColEnd && !isColPrefix)
						|| (isColEnd && !isColWord)){
					rowStr[row].deleteCharAt(rowStr[row].length() - 1);
					colStr[col].deleteCharAt(colStr[col].length() - 1);
					continue;
				}

				filledBoard[row][col] = cur;
				char[][] result = recursiveMethod(emptyBoard, filledBoard, dictionary, rowStr, colStr, row, col);
				if(result != null){
					return result;
				} else{
					rowStr[row].deleteCharAt(rowStr[row].length() - 1);
					colStr[col].deleteCharAt(colStr[col].length() - 1);
				}
			}
		}else {
			rowStr[row].append(character);
			colStr[col].append(character);

			if(character != '-'){
				boolean isRowPrefix = isPrefix(rowStr[row], rowStr[row].lastIndexOf("-") + 1, rowStr[row].length() - 1, dictionary);
				boolean isColPrefix = isPrefix(colStr[col], colStr[col].lastIndexOf("-") + 1, colStr[col].length() - 1, dictionary);
				boolean isRowWord = isWord(rowStr[row], rowStr[row].lastIndexOf("-") + 1, rowStr[row].length() - 1, dictionary);
				boolean isColWord = isWord(colStr[col], colStr[col].lastIndexOf("-") + 1, colStr[col].length() - 1, dictionary);
				boolean isRowEnd = (col == emptyBoard[0].length - 1) || (emptyBoard[row][col + 1] == '-');
				boolean isColEnd = (row == emptyBoard.length - 1) || (emptyBoard[row + 1][col] == '-');

				if ((!isRowEnd && !isRowPrefix)
						|| (isRowEnd && !isRowWord)
						|| (!isColEnd && !isColPrefix)
						|| (isColEnd && !isColWord)){
					rowStr[row].deleteCharAt(rowStr[row].length() - 1);
					colStr[col].deleteCharAt(colStr[col].length() - 1);
					return null;
				}

			}

			filledBoard[row][col] = character;
			char[][] result = recursiveMethod(emptyBoard, filledBoard, dictionary, rowStr, colStr, row, col);
			if(result != null){
				return result;
			} else{
				rowStr[row].deleteCharAt(rowStr[row].length() - 1);
				colStr[col].deleteCharAt(colStr[col].length() - 1);
				return null;
			}
		}

		return null;
	}

	public boolean isPrefix(StringBuilder sb, int start, int end, DictInterface dict){
    	if(dict.searchPrefix(sb, start, end) % 2 == 0)
    		return false;
    	else
    		return true;
	}

	public boolean isWord(StringBuilder sb, int start, int end, DictInterface dict){
		if(dict.searchPrefix(sb, start, end) < 2)
			return false;
		else
			return true;
	}
    /*
     * checks if filledBoard is a correct fill for emptyBoard
     * @param emptyBoard is a 2-d array representing an empty board
     * @param filledBoard is a 2-d array representing a filled out board
     * @param dictionary is the dictinary to be used for checking the puzzle
     * @return true if rules defined in fillPuzzle has been followed and 
     *  that every row and column is a valid word in the dictionary. If a row
     *  a column has one or more '-' in it, then each segment separated by 
     * the '-' should be a valid word in the dictionary 
     */
    public boolean checkPuzzle(char[][] emptyBoard, char[][] filledBoard, DictInterface dictionary){
		StringBuilder[] rowStr = new StringBuilder[emptyBoard.length];
		StringBuilder[] colStr = new StringBuilder[emptyBoard[0].length];
		for(int i = 0; i < rowStr.length; i++){
			rowStr[i] = new StringBuilder();
		}
		for(int i = 0; i < colStr.length; i++){
			colStr[i] = new StringBuilder();
		}

    	for(int i=0; i<emptyBoard.length; i++){
			for(int j=0; j<emptyBoard[0].length; j++) {
				char character = emptyBoard[i][j];

				if(character == '+'){
					if(filledBoard[i][j] == '-'){
						return false;
					}
					rowStr[i].append(filledBoard[i][j]);
					colStr[j].append(filledBoard[i][j]);
				}else if(character == '-') {
					if(filledBoard[i][j] != character){
						//System.out.println("1");
						return false;
					}

					if(j > 0 && rowStr[i].length() > 0 && rowStr[i].lastIndexOf("-") < rowStr[i].length() - 1 && !isWord(rowStr[i], rowStr[i].lastIndexOf("-") + 1, rowStr[i].length() - 1, dictionary)){
						//System.out.println("2 i:" + i +" j:" + j);
						return false;
					}
					if(i > 0 && colStr[j].length() > 0 && colStr[j].lastIndexOf("-") < colStr[j].length() - 1 && !isWord(colStr[j], colStr[j].lastIndexOf("-") + 1, colStr[j].length() - 1, dictionary)){
						//System.out.println("3");
						return false;
					}

					rowStr[i].append(character);
					colStr[j].append(character);
				}else{
					if(filledBoard[i][j] != character){
						//System.out.println("4");
						return false;
					}

					rowStr[i].append(character);
					colStr[j].append(character);
				}

				if(j == emptyBoard[0].length - 1 && rowStr[i].lastIndexOf("-") < rowStr[i].length() - 1 && !isWord(rowStr[i], rowStr[i].lastIndexOf("-") + 1, rowStr[i].length() - 1, dictionary)){
					//System.out.println("5");
					return false;
				}

				if(i == emptyBoard.length - 1 && colStr[j].lastIndexOf("-") < colStr[j].length() - 1 && !isWord(colStr[j], colStr[j].lastIndexOf("-") + 1, colStr[j].length() - 1, dictionary)){
					//System.out.println("6");
					return false;
				}
			}
		}
		return true;
	}

}
