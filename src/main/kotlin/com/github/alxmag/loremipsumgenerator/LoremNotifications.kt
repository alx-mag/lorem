package com.github.alxmag.loremipsumgenerator

import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationGroupManager

object LoremNotifications {

    private const val GroupId = "alxmag.LoremNotifications"

    fun getGroup(): NotificationGroup = NotificationGroupManager.getInstance().getNotificationGroup(GroupId)
}