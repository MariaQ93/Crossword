# Assignment 1 Wrtire-Up

## Problem Solving Processes
`recursiveMethod()`<br />
- The purpose of this function is to use recursion to find the letters that should be placed in each square and are valid. 
- First we need to determine when the program returns, when the value of row is equal to the last position of the board row
and the value of col is equal to the last position of the board col, then the program has found all the letters that can form the word and put them into the fillboard At this time, we can return fillboard directly.
- Then, when the corresponding value of the input board is a minus sign, it can be appended directly to the StringBuilder.
- When the corresponding value of the input board is a plus sign, try filling in the spaces in order from a to z. For each value, determine whether stringbuilder constitutes a prefix or a complete word, and depending on its position. If the current position does not constitute a word or prefix, we will backtracking to the perious node and try its next letter, then look for it. Keep cycling through this step until we find the right word. If we find it, we continue recursively to determine the next position, and if the recursive result is empty, we continue to try the next letter until we have traversed the whole board, and if it is not empty, we return directly.
- When the corresponding value of the input board is a pre-filled, I add it directly to the StringBuilder, and if it can form a prefix or word, we continue down the line, if not, we will go back and return.

`isPrefix()`  <br />
- The isPrefix function is used to determine if the stringbuilder is a valid prefix within the specified range([start, end] inclusive) by calling the Dictionary Interface.

`isWord()`  <br />
- The isWord function is used to determine if the stringbuilder is a valid word within the specified range([start, end] inclusive) by calling the Dictionary Interface.

`fillPuzzle()` <br />
- The purpose of fillPuzzel is to create a row and col StringBuilder for the recursiveMethod, then new a new board to store the result, and pass it into the created recursivemethod for the fillboard operation.

`checkPuzzle()`  <br />

- checkPuzzle is used to check the validity of the filled board by comparing it with emptyBoard and to search for each word in the dictionary by calling the Dictionary interface.
- To do this, it also creates two StringBuilder arrays to represent each row and each column. This method iterates over each node in the filledBoard. When the iteration encounters a minus character or reaches the edge or bottom, it checks the dictionary with the corresponding StringBuilder. It will then clear the stringbuilder for the next node. so, each time, the method checks the entire stringbuilder, not the range as specified above.

`Coding issues`  <br />
- When reaching the end of a row or column, I should consider whether the current word constitutes a word rather than a prefix.
- I forgot to consider the case when there are two minus.

<details>
<summary><span style="font-size: 19px">Approximate Run-Times for each files</span></summary>
<p>


| Text Files | Approximent Run-Times                  |
|------------|----------------------------------------|
| `test3a`   | less than 1s                           |
| `test3b`   | less than 1s                           |
| `test4a`   | 9.70s                                  |
| `test4b`   | 8.70s                                  |
| `test4c`   | less than 1s                           |
| `test4d`   | less than 1s                           |
| `test4e`   | greater than 30min                     |
| `test4f`   | 1.86s                                  |
| `test5a`   | 7.24s                                  |
| `test6a`   | greater than 30min                     |
| `test6b`   | about 24min                            |
| `test6c`   | greater than 30min                     |
| `test7a`   | greater than 30min                     |
| `test8a`   | greater than 30min                     | 
| `test8b`   | greater than 30min                     |
| `test8c`   | about 15min                            |

</p>
</details>

<details>
<summary><span style="font-size: 19px">Asymptotic Analysis of Worst Cases</span></summary>
<p>

#### The worse run time should be 26^n, or O(2^n).

```
There are 26 possibilities for each node, 
having n nodes means that there will be n times cycle, 
and combination can be multiplied by each other.
```

</p>
</details>
