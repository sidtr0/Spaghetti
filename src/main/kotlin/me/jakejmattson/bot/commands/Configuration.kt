package me.jakejmattson.bot.commands

import dev.kord.common.entity.Permission
import me.jakejmattson.discordkt.api.dsl.commands
import me.jakejmattson.bot.conversations.ConfigurationConversation
import me.jakejmattson.bot.data.Configuration

fun configurationCommands() = commands("Configuration") {
    guildCommand("Configure") {
        description = "Start a conversation in the chat to configure the bot for your server."
        execute {
            if (message.getAuthorAsMember()?.getPermissions()?.contains(Permission.Administrator) == true) {
                val result = ConfigurationConversation(Configuration()).configurationConversation(guild).startPublicly(discord, author, channel)
                println(result)
            } else {
                respond("Sorry, you do not have permission to run this command.")
            }
        }
    }
}