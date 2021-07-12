// Thank you, ddivad!

package me.jakejmattson.bot.extensions

import dev.kord.common.entity.Permission
import me.jakejmattson.discordkt.api.dsl.Command
import me.jakejmattson.discordkt.api.dsl.precondition

val commandPermissions: MutableMap<Command, Permission> = mutableMapOf()
val defaultPermission = Permission.SendMessages
var Command.requiredPermission: Permission
    get() = commandPermissions[this] ?: defaultPermission
    set(value) {
        commandPermissions[this] = value
    }

fun permissionPrecondition() = precondition {
    val requiredPermission = command?.requiredPermission ?: defaultPermission
    if (this.author.asMember(guild!!.id).getPermissions().contains(requiredPermission)) return@precondition
    else fail("Missing required permission!")
}