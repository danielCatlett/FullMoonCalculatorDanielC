package com.example;
import java.util.Scanner;

public class MyClass
{

    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);

        int month=enterValue("month",1,12);

        int year=enterValue("year",2000,2999);

        boolean leapYear=checkLeapYear(year);
        int monthLength=calculateMonthLength(month,leapYear);
        int day=enterValue("day",1,monthLength);

        if(year == 2000 && month == 1 && day <= 20)
            System.out.println("The nearest full moon to the given date is 1/20/2000");
        else
        {
            int daysSinceFirstFullMoon = calcDifference(month, day, year); //Num of days between given date and 1/1/2000
            double numOfFullMoons = (Math.round(daysSinceFirstFullMoon/29.530588861)); //Num of full moons since 1/1/2000, given that 29.530588861 is the number of days between full moons

            //Formula for finding full moon, solution is number of days after 1/1/2000 the full moon takes place
            int daysToCount = (int) (20.362955 + 29.530588861*numOfFullMoons + 102.26*Math.pow(10, -12)*Math.pow(numOfFullMoons, 2));

            findDateofFullMoon(daysToCount);
        }
    }

    public static boolean checkLeapYear(int year)
    {
        if(year%400==0)
            return true;
        else if(year%100==0)
            return false;
        else if(year%4==0)
            return true;
        else
            return false;
    }

    public static int yearLength(int year)
    {
        if(checkLeapYear(year))
            return 366;
        else
            return 365;
    }

    public static int enterValue(String type, int lowerBound, int upperBound)
    {
        Scanner in=new Scanner(System.in);

        String errorMessage;
        if(type.equals("month"))
            errorMessage="There are only 12 months in a year.\nOr maybe you entered a number less than zero.\nEither way, try again.";
        else if(type.equals("day"))
            errorMessage="The given month does not have that many days.\nOr maybe you entered a number less than zero.\nEither way, try again.";
        else
            errorMessage="This program only accepts years between 2000 and 2999, inclusive. Please enter a different year.";


        System.out.println("Enter a "+type+" in numbers");
        int returning=in.nextInt();
        while(returning<lowerBound || returning>upperBound)
        {
            System.out.println(errorMessage);
            returning=in.nextInt();
        }

        return returning;
    }

    public static int calculateMonthLength(int month, boolean leapYear)
    {
        if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12)
            return 31;
        else if(month==2 && leapYear)
            return 29;
        else if(month==2)
            return 28;
        else
            return 30;
    }

    public static int calcDifference(int month, int day, int year)
    {

        int baseDay = 20;
        int baseMonth = 1;
        int baseYear = 2000;

        int secondDay = day;
        int secondMonth = month;
        int secondYear = year;

        //Find the number of full, uninterrupted years between the dates
        int fullYears = secondYear - baseYear;
        if(fullYears > 0)
            fullYears--;

        //Add that total to output
        int dif=fullYears*365;

        //find out how many of the full years are leap years
        int leapYears = 0;
        for(int i = baseYear+1; i < secondYear; i++)
        {
            if(checkLeapYear(i))
                leapYears++;
        }

        //add those extra leap year days to the total
        dif += leapYears;

        //If the dates take place during two different years
        if(secondYear - baseYear >= 1)
        {
            //Find the rest of the days in the first year (not including the rest of the days in
            //that month, that is found later), and add them to the total
            for (int i = baseMonth+1; i <= 12; i++)
            {
                dif += calculateMonthLength(i, checkLeapYear(i));
            }

            //Add all the days in the second year leading up to the second date (again, not
            //including days contained within second month) to the total
            for (int i = 1; i < secondMonth; i++)
            {
                dif += calculateMonthLength(i, checkLeapYear(secondYear));
            }
        }
        //If both dates take place during the same year
        else
        {
            //Add up the days in the months between the two given months
            for (int i = baseMonth+1; i < secondMonth; i++)
            {
                dif += calculateMonthLength(i, checkLeapYear(2016));
            }
        }

        //If both dates do not take place during October 2016
        if(secondYear!=baseYear || (secondYear==baseYear && secondMonth!=baseMonth))
        {
            dif += (calculateMonthLength(baseMonth, checkLeapYear(baseYear)) - baseDay); //Add rest of first month
            dif += secondDay; //Add beginning of second month
        }
        else //If both dates take place during October 2016
        {
            dif += secondDay - baseDay;
        }

        return dif;
    }

    public static void findDateofFullMoon(int days)
    {
        int month = 1;
        int year = 2000;

        //Subtract a years worth of days until we have the correct year
        while(days > yearLength(year))
        {
            days -= yearLength(year);
            year++;
        }

        //Subtract a month's worth of days until we have the correct month
        while(days > calculateMonthLength(month, checkLeapYear(year)))
        {
            days -= calculateMonthLength(month, checkLeapYear(year));
            month++;
        }

        days++;

        System.out.println("The nearest full moon to the given date will take place on " + month + "/" + days + "/" + year);
    }
}
