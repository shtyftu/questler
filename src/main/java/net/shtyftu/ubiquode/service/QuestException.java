package net.shtyftu.ubiquode.service;

/**
 * @author shtyftu
 */
abstract class QuestException extends Exception {

    QuestException() {
        super();
    }

    QuestException(String s) {
        super(s);
    }

    static class WrongStateException extends QuestException {

    }

    static class OnlyDeadlineLowScoreUserException extends QuestException {

    }

    static class UserUnableToLockException extends QuestException {

        UserUnableToLockException(String s) {
            super(s);
        }
    }
}
