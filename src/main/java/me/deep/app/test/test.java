package me.deep.app.test;

public class test {
  public static int[][] map = { //
          { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, //
          { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 3, 0, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 3, 0, 0, 0, 3, 0, 1, 1, 1, 0, 1, 1, 1 }, //
          { 1, 0, 3, 0, 0, 0, 3, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 3, 3, 0, 3, 3, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 }, //
          { 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 0, 4, 4, 4 }, //
          { 1, 0, 0, 0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 4 }, //
          { 1, 0, 0, 0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 4 }, //
          { 1, 0, 0, 0, 0, 0, 1, 4, 0, 3, 3, 3, 3, 0, 4 }, //
          { 1, 0, 0, 0, 0, 0, 1, 4, 0, 3, 3, 3, 3, 2, 4 }, //
          { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4 }, //
          { 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4 } };//
  static String cities[] = { "New York", "Lagos", "Bangalore", "Delhi", "San Francisco", "Los Angeles", "Las Vegas",
          "London", "Berlin", "Moscow", "Paris", "Sydney", "Auckland", "Bankok", "Cario", "Barsilla", "Tornoto", "Rome",
          "Warsaw", "Mecca", "Jerusalem", "Beijing", "Shangai", "Hong Kong", "Madrid", "Mexico City", "Singapur",
          "Tokoyo", "Mumbai", "Dubai", "Buenos Aires", "Kuala Lumpur", "Jakarta", "Seattle", "Dallas", "Washington D C",
          "Pune", "Chennai", "Melborne", "Colombo", "Zuric", "Mountain Veiw", "Boston", "Atlanta", "Maimi", "Taipai" };

  public static void main(String[] args) {
    String h = "<deep>hello~////~deeptestdeep~////~deepdeeptest";
    String chat[] = h.split("~////~", 2);
    System.out.println(chat[0] + chat[1] + chat.length);
  }

}
