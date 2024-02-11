package comp1110.ass2;

public class HiddenState {
    //public static boolean isHiddenStateWellFormed(String[] hiddenState) {

    public static boolean checkTwoCharacter(String str) {


        if (str.charAt(0) == 'a' || str.charAt(0) == 'b' || str.charAt(0) == 'c' || str.charAt(0) == 'd' || str.charAt(0) == 'j' || str.charAt(0) == 'm'
                && (Integer.parseInt(String.valueOf(str.charAt(1))) >= 1) && (Integer.parseInt(String.valueOf(str.charAt(1))) <= 8)) {
            return true;

        }
        return false;
    }


    public static boolean compareTwoString(String str1, String str2) {
        if (str1.charAt(0) > str2.charAt(0) && str1.charAt(1) > str2.charAt(1) || str1.charAt(0) == str2.charAt(0) && str1.charAt(1) > str2.charAt(1)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean HiddenState(String[] hiddenState) {

        if (hiddenState.length == 0 || hiddenState.length == 1 || hiddenState.length == 2) {
            return false;
        }

        if (hiddenState[0].equals("") && hiddenState[1].equals("") && hiddenState[2].equals("")) {
            return false;
        }


        for (int j = 0; j < 3; j++) {

            if (j == 0) {


                if (hiddenState[0].length() % 2 == 0) {

                    for (int i = 0; i < hiddenState[0].length() - 3; i += 2) {

                        String str1 = hiddenState[0].substring(i, i + 2);
                        String str2 = hiddenState[0].substring(i + 2, i + 4);

                        if (checkTwoCharacter(str1) && checkTwoCharacter(str2)) {


                            if (!compareTwoString(str1, str2)) {
                                return false;
                            }

                        } else {
                            return false;
                        }

                    }

                } else {
                    return false;
                }

            }


            if (j == 1) {
                if (hiddenState[1].equals("")) {
                    return false;
                }

                if (hiddenState[1].charAt(0) == 'A') {

                    if (hiddenState[1].length() - 1 != 0) {

                        if ((hiddenState[1].length() - 1) % 2 == 0) {
                            if ((hiddenState[1].length() - 1) / 2 == 7 || (hiddenState[1].length() - 1) / 2 == 8 || (hiddenState[1].length() - 1) / 2 == 9) {

                                for (int i = 1; i < hiddenState[1].length() - 3; i += 2) {

                                    String str1 = hiddenState[1].substring(i, i + 2);
                                    String str2 = hiddenState[1].substring(i + 2, i + 4);


                                    if (checkTwoCharacter(str1) && checkTwoCharacter(str2)) {


                                        if (!compareTwoString(str1, str2)) {
                                            return false;
                                        }

                                    } else {
                                        return false;
                                    }

                                }

                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }

                    } else {
                        return true;
                    }


                } else {
                    return false;
                }

            }


            if (j == 2) {

                if (hiddenState[2].equals("")) {
                    return false;
                }

                if (hiddenState[2].charAt(0) == 'B') {

                    if (hiddenState[2].length() - 1 != 0) {

                        if ((hiddenState[2].length() - 1) % 2 == 0) {

                            if ((hiddenState[2].length() - 1) / 2 == 7 || (hiddenState[2].length() - 1) / 2 == 8 || (hiddenState[2].length() - 1) / 2 == 9) {

                                for (int i = 1; i < hiddenState[2].length() - 3; i += 2) {

                                    String str1 = hiddenState[2].substring(i, i + 2);
                                    String str2 = hiddenState[2].substring(i + 2, i + 4);


                                    if (checkTwoCharacter(str1) && checkTwoCharacter(str2)) {


                                        if (!compareTwoString(str1, str2)) {
                                            return false;
                                        }

                                    } else {
                                        return false;
                                    }

                                }

                            }

                        } else {
                            return false;
                        }


                    } else {
                        return true;
                    }


                } else {
                    return false;
                }

            }
        }


        return true;

    }
}