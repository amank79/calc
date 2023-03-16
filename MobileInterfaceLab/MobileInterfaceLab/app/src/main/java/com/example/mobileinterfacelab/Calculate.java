package com.example.mobileinterfacelab;

import java.util.ArrayList;
import java.util.Stack;
public class Calculate {

    static int calculator(String s) {



        ArrayList<String> list=split(s);
        ArrayList<String> postfix=convertToPostfix(list);
        return  calculate(postfix);


    }
    static boolean isOperand(char a)
    {
        return !(a=='+'||a=='-'||a=='/'||a=='*'||a=='('||a==')'||a=='$'||a=='#');
    }
    static boolean specialOp(char a)
    {
        return !(a=='+'||a=='-'||a=='/'||a=='*'||a=='#'||a=='$');
    }

    static  boolean isUnary(String exp,int i)
    {
        if (exp.charAt(i)=='+'||exp.charAt(i)=='-')
        {
            if (i==0)
                return true;
            char temp=exp.charAt(i-1);
            if (!specialOp(temp))
                return true;
            else
                return false;
        }
        else
            return false;


    }
    public static ArrayList<String> split(String exp)
    {
        ArrayList<String> list=new ArrayList<>();


        for(int i=0;i<exp.length();)

        {
            if (exp.charAt(i)!=' ') {
                if (isOperand(exp.charAt(i))) {
                    String temp = ""+exp.charAt(i);
                    i++;
                    while (i < exp.length() && isOperand(exp.charAt(i))) {
                        if (exp.charAt(i) != ' ')
                            temp += exp.charAt(i);
                        i++;
                    }
                    list.add(temp);
                }
                else
                {


                    if (isUnary(exp,i))
                    {
                        if (exp.charAt(i)=='+')
                            list.add("#");
                        else
                            list.add("$");
                    }
                    else
                        list.add(""+exp.charAt(i));
                    i++;
                }
            }
            else
                i++;

        }



        return list;
    }

    static int getPrecedence(char op)
    {
        if (op=='/'||op=='*')
            return 2;
        else if (op=='+'||op=='-')
            return 1;
        else if (op=='$'||op=='#')
            return 3;
        return -1; // we know () has higher precedence but to avoid pop operation of ( when new operator are pushed we are considering this


    }
    public static  ArrayList<String> convertToPostfix(ArrayList<String> exp) {
        ArrayList<String> postfix=new ArrayList<>();
        Stack<String> stack=new Stack<>();
        for(String te:exp)
        {
            if (isOperand(te.charAt(0)))
                postfix.add(te);
            else if (te.charAt(0)=='(')
            {
                stack.push("(");
            }
            else if (te.charAt(0)==')')
            {

                while(!(stack.peek().equals("(")))
                    postfix.add(stack.pop());
                stack.pop();
            }
            else
            {
                if (stack.size()>=1)
                {
                    int t1=getPrecedence(te.charAt(0));
                    int t2=getPrecedence(stack.peek().charAt(0));
                    if (t1>t2)
                        stack.push(te);
                    else
                    {
                        while(stack.size()>=1&&t1<=getPrecedence(stack.peek().charAt(0)))
                        {
                            postfix.add(stack.pop());
                        }
                        stack.add(te);
                    }
                }
                else
                    stack.push(te);
            }
        }
        while(!stack.isEmpty())
            postfix.add(stack.pop());

        return postfix;
    }

    static int calculate(ArrayList<String> postfix)
    {

        Stack<String> stack=new Stack<>();
        for (String tem:postfix)
        {
            if (isOperand(tem.charAt(0)))
                stack.push(tem);
            else
            {

                String a=null,b=null;
                if (tem.charAt(0)=='$'||tem.charAt(0)=='#')
                {
                    b=stack.pop();
                    if (tem.charAt(0)=='#')
                        stack.push(b);
                    else
                        stack.push('-'+b);
                }
                else
                {
                    b=stack.pop();
                    a=stack.pop();
                }
                if (tem.charAt(0)=='+')
                {
                    Integer res =(Integer.parseInt(a)+Integer.parseInt(b));
                    stack.push(res.toString());
                }
                else if (tem.charAt(0)=='-')
                {
                    Integer res =(Integer.parseInt(a)-Integer.parseInt(b));
                    stack.push(res.toString());
                }
                else if (tem.charAt(0)=='*')
                {
                    Integer res =(Integer.parseInt(a)*Integer.parseInt(b));
                    stack.push(res.toString());
                }
                else if (tem.charAt(0)=='/')
                {
                    Integer res =(Integer.parseInt(a)/Integer.parseInt(b));
                    stack.push(res.toString());
                }
            }
        }
        return Integer.parseInt(stack.pop());
    }







}

