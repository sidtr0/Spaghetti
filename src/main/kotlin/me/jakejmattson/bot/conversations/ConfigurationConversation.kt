package me.jakejmattson.bot.conversations

import me.jakejmattson.bot.data.Configuration
import dev.kord.core.entity.Guild
import me.jakejmattson.discordkt.api.arguments.*
import me.jakejmattson.discordkt.api.conversations.conversation

fun configurationConversation(configuration: Configuration, guild: Guild) = conversation("exit") {
    val serverPrefix = promptMessage(AnyArg, "What should the server prefix be?")
    val staffReviewChannel = promptMessage(ChannelArg, "In which channel should all suggestions first be forwarded to for staff review? Please make sure this is not a public channel!")
    val publicVotingChannel = promptMessage(ChannelArg, "After review, which channel should the suggestions go to for public voting?")

    respond("Server configured. Server's prefix is `$serverPrefix` and staff review channel is <#${staffReviewChannel.id.value}>. Suggestions will be sent to <#${publicVotingChannel.id.value}>.")
    configuration.setup(guild, serverPrefix, staffReviewChannel, publicVotingChannel)
}
