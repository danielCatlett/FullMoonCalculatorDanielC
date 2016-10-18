package com.example;

public class MyClass
{
    public static void main (String[] args)
    {
        double output=29.53058853;
        for(int i=0; i<1000; i++)
        {
            System.out.println(output);
            output+=29.53058853;
        }
    }
}
