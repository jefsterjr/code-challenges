import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/*
1
5
1
2
3
1
2
*/
public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int x = Integer.parseInt(bufferedReader.readLine().trim());

        int spaceCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> space = IntStream.range(0, spaceCount).mapToObj(i -> {
            try {
                return bufferedReader.readLine().replaceAll("\\s+$", "");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(toList());

        System.out.println(segment(x, space));


        bufferedReader.close();
    }

    public static int segment(int x, List<Integer> space) {
        int chunkNum = 1;
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(0);

        for (int i = 1; i < space.size(); i++) {
            if (i < x) {
                if (space.get(i) < space.get(stack.peek())) {
                    stack.pop();
                    stack.push(i);
                }
            } else {
                Integer peek = stack.peek();
                if (peek >= chunkNum) {
                    stack.push(space.get(i) < space.get(peek) ? i : peek);

                } else {
                    stack.push(i);
                    int j = chunkNum;
                    int count = 0;
                    while (count++ < x) {
                        if (space.get(j) < space.get(stack.peek())) {
                            stack.pop();
                            stack.push(j);
                        }
                        j++;
                    }
                }
                chunkNum++;
            }

        }
       return Collections.max(stack.stream().filter(space::contains).collect(toList()));
    }
