package me.jakejmattson.bot.conversations

import dev.kord.common.entity.Permission
import me.jakejmattson.discordkt.api.arguments.*
import me.jakejmattson.discordkt.api.dsl.*
import me.jakejmattson.discordkt.api.conversations.conversation

fun configurationConversation() = conversation("exit") {
    val serverPrefix = promptMessage(AnyArg, "What should the server prefix be?")
    val staffReviewChannel = promptMessage(ChannelArg, "In which channel should all suggestions first be forwarded to for staff review? Please make sure this is not a public channel!")
    val publicVotingChannel = promptMessage(ChannelArg, "After review, which channel should the suggestions go to for public voting?")

    respond("Server configured. Server's prefix is $serverPrefix and staff review channel is #${staffReviewChannel.name}. Suggestions will be sent to #${publicVotingChannel.name}.\nInvoke this command again for making changes.")
}

fun configurationCommands() = commands("Configuration") {
    guildCommand("Configure") {
        description = "Start a conversation in the chat to configure the bot for your server."
        execute {
            if (message.getAuthorAsMember()?.getPermissions()?.contains(Permission.Administrator) == true) {
                val result = configurationConversation().startPublicly(discord, author, channel)
                println(result)
            } else {
                respond("Sorry, you do not have permission to run this command.")
            }
        }
    }
}