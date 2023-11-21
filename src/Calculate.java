import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculate {
    private static final Stack<String> stack = new Stack<>();

    public String Evaluation(String infix, boolean IsDegree) {
        String[] postfix = InfixToPostfix(infix);

        for (String postfix1 : postfix) {
            if (postfix1 == null) {
                break;
            }

            String c = postfix1;
            if (IsOperator(c)) {
                if (c.equals("-") || c.equals("+") || c.equals("×") || c.equals("÷") || c.equals("^")) {
                    double op2 = Double.parseDouble(stack.pop());
                    double op1 = Double.parseDouble(stack.pop());

                    double result = 0;
                    switch (c) {
                        case "+":
                            result = op1 + op2;
                            stack.push(String.valueOf(result));
                            break;
                        case "-":
                            result = op1 - op2;
                            stack.push(String.valueOf(result));
                            break;
                        case "×":
                            result = op1 * op2;
                            stack.push(String.valueOf(result));
                            break;
                        case "÷":
                            result = op1 / op2;
                            stack.push(String.valueOf(result));
                            break;
                        case "^":
                            result = Math.pow(op1, op2);
                            stack.push(String.valueOf(result));
                            break;
                    }
                } else {
                    double op1 = Double.parseDouble(stack.pop());
                    double result = 0;
                    switch (c) {
                        case "tan":
                            result = (IsDegree) ? Math.tan(op1 * Math.PI / 180.0) : Math.tan(op1);
                            stack.push(String.valueOf(result));
                            break;
                        case "cos":
                            result = (IsDegree) ? Math.cos(op1 * Math.PI / 180.0) : Math.cos(op1);
                            stack.push(String.valueOf(result));
                            break;
                        case "sin":
                            result = (IsDegree) ? Math.sin(op1 * Math.PI / 180.0) : Math.sin(op1);
                            stack.push(String.valueOf(result));
                            break;
                        case "log":
                            result = Math.log10(op1);
                            stack.push(String.valueOf(result));
                            break;
                        case "ln":
                            result = Math.log(op1);
                            stack.push(String.valueOf(result));
                            break;
                        case "sqrt":
                            result = Math.sqrt(op1);
                            stack.push(String.valueOf(result));
                            break;
                    }
                }
            } else {
                // Ganti nilai 'a' dengan nilai negatif
                String modifiedOperand = modifyOperand(c);
                stack.push(modifiedOperand);
            }
        }
        return stack.pop();
    }

    private boolean IsOperator(String input) {
        char firstChar = input.charAt(0);
        return !(Character.isDigit(firstChar) || firstChar == 'a');
    }

    private static int Priority(String c) {
        switch (c) {
            case "+":
            case "-":
                return 0;
            case "×":
            case "÷":
                return 1;
            case "^":
                return 2;
            case "log":
            case "sin":
            case "ln":
            case "sqrt":
            case "cos":
            case "tan":
                return 3;
            default:
                break;
        }
        return -1;
    }

    private String[] InfixToPostfix(String Input) {
        String[] postfix = new String[Input.length()];
        int k = 0;
        for (int i = 0; i < Input.length(); i++) {
            postfix[k] = "";
            String c = String.valueOf(Input.charAt(i));

            if (Character.isDigit(c.charAt(0))) {
                while (Character.isDigit(Input.charAt(i)) || Input.charAt(i) == '.') {
                    postfix[k] = postfix[k] + String.valueOf(Input.charAt(i));
                    i++;
                    if (i == Input.length()) {
                        break;
                    }
                }
                i--;
                k++;
            } else if (c.equals("(")) {
                stack.push(c);
            } else if (c.equals(")")) {
                while (!String.valueOf(stack.peek()).equals("(")) {
                    postfix[k] = String.valueOf(stack.pop());
                    k++;
                }
                stack.pop();
            } else if (c.equals("-") || c.equals("+") || c.equals("×") || c.equals("÷") || c.equals("^") || c.equals("s") || c.equals("c") || c.equals("t") || c.equals("l")) {
                String operator = "";
                int count = 0;
                while (i < Input.length() && Character.isLetter(Input.charAt(i))) {
                    operator += String.valueOf(Input.charAt(i));
                    i++;
                    count++;
                }
                if (count > 0) {
                    stack.push(operator);
                    i--;
                } else if (count == 0) {
                    while (stack.size() > 0 && !String.valueOf(stack.peek()).equals("(") && Priority(c) <= Priority(String.valueOf(stack.peek()))) {
                        postfix[k] = String.valueOf(stack.pop());
                        k++;
                    }
                    stack.push(c);
                }
            }
        }
        while (stack.size() > 0) {
            postfix[k] = String.valueOf(stack.pop());
            k++;
        }
        return postfix;
    }
    private String modifyOperand(String operand) {
        // Ganti nilai 'a' dengan nilai negatif
        Pattern pattern = Pattern.compile("(-?[0-9]+)a");
        Matcher matcher = pattern.matcher(operand);
        if (matcher.find()) {
            String value = matcher.group(1);
            return "-" + value;
        } else {
            return operand;
        }
    }
    
    

    public String InToPoText(String Input) {
        String[] postfix = InfixToPostfix(Input);
        StringBuilder p = new StringBuilder();

        for (String postfix1 : postfix) {
            if (postfix1 != null) {
                p.append(postfix1);
            }
        }

        return "Postfix = " + p.toString();
    }
}
