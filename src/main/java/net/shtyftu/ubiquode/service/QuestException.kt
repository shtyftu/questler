package net.shtyftu.ubiquode.service

/**
 * @author shtyftu
 */
internal abstract class QuestException : Exception {

    constructor() : super()

    constructor(s: String) : super(s)

    internal class WrongStateException : QuestException()

    internal class OnlyDeadlineLowScoreUserException : QuestException()

    internal class UserUnableToLockException(s: String) : QuestException(s)
}
