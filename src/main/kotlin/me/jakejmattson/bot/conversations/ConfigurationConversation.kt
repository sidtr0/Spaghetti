package me.dracthedino.bot.conversations

import me.jakejmattson.discordkt.api.arguments.*
import me.jakejmattson.discordkt.api.dsl.*

fun configurationConversation() = conversation("exit") {
    val serverPrefix = promptMessage(AnyArg, "What should the server prefix be?")
    val staffReviewChannel = promptMessage(ChannelArg, "In which channel should all suggestions first be forwarded to for staff review?")

    respond("Server configured. Server's prefix is $serverPrefix and staff review channel is ${staffReviewChannel.name}. Invoke this command again for making changes.")
}

fun configurationCommands() = commands("Configuration") {
    command("configure") {
        description = "Start a conversation in the chat to configure the bot for your server."
        execute {
            val result = configurationConversation().startPublicly(discord, author, channel)
            println(result)
        }
    }
}