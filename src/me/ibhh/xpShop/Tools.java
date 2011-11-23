package me.ibhh.xpShop;


public class Tools
{
  public static boolean isInteger(String input)
  {
    try
    {
      Integer.parseInt(input);
      return true;
    } catch (Exception e) {
    }
    return false;
  }
}