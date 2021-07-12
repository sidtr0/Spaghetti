package me.jakejmattson.bot.commands

import me.jakejmattson.bot.extensions.requiredPermission
import dev.kord.common.entity.Permission
import me.jakejmattson.discordkt.api.dsl.commands
import me.jakejmattson.bot.conversations.ConfigurationConversation
import me.jakejmattson.bot.data.Configuration
import dev.kord.common.kColor
import java.awt.Color

fun configurationCommands() = commands("Configuration") {
    guildCommand("Configure") {
        requiredPermission = Permission.Administrator
        description = "Start a conversation in the chat to configure the bot for your server."
        execute {
                ConfigurationConversation(Configuration()).configurationConversation(guild).startPublicly(discord, author, channel)
        }
    }
    guildCommand("ShowConfiguration") {
        description = "Show the server's current configuration."
        execute {
            val prefix = "s!"
            val guildConfiguration = discord.getInjectionObjects(Configuration::class).guildConfigurations
            respond {
                title = "Guild Configuration for Guild: ${guild.name}"
                color = Color.ORANGE.kColor
                field {
                    name = "Prefix"
                    value = "`${guild?.let { guildConfiguration[it.id.value]?.serverPrefix } ?: prefix}`"
                }
                field {
                    name = "Staff Review Channel"
                    value = "<#${guild?.let { guildConfiguration[it.id.value]?.staffReviewChannel?.asString }}>"
                }
                field {
                    name = "Public Voting Channel"
                    value = "<#${guild?.let { guildConfiguration[it.id.value]?.publicVotingChannel?.asString }}>"
                }
            }
        }
    }
}