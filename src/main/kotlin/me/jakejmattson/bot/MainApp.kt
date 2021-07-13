package me.jakejmattson.bot

import dev.kord.common.annotation.KordPreview
import dev.kord.gateway.Intents
import dev.kord.common.kColor
import kotlinx.coroutines.flow.toList
import me.jakejmattson.bot.data.Configuration
import me.jakejmattson.discordkt.api.dsl.bot
import me.jakejmattson.discordkt.api.extensions.*
import java.awt.Color

@KordPreview
suspend fun main(args: Array<String>) {
    //Get the bot token from the command line (or your preferred way).
    val token = args.firstOrNull()
    require(token != null) { "Expected the bot token as a command line argument!" }

    val defaultPrefix = "s!"

    //Start the bot and set configuration options.

    bot(token) {
        //Dynamically determine the prefix used for commands.
        prefix {
            val guildConfiguration = discord.getInjectionObjects(Configuration::class).guildConfigurations
            guild?.let { guildConfiguration[it.id.value]?.serverPrefix } ?: defaultPrefix
        }

        //Simple configuration options
        configure {
            //An emoji added when a command is invoked (use 'null' to disable this).
            commandReaction = null
            //A color constant for your bot - typically used in embeds.
            theme = Color.ORANGE
        }

        //An embed sent whenever someone solely mentions your bot ('@Bot').
        mentionEmbed {
            title = "Spaghetti"
            color = it.discord.configuration.theme!!.kColor

            author {
                with(it.author) {
                    icon = avatar.url
                    name = tag
                    url = profileLink
                }
            }

            thumbnail {
                url = it.discord.kord.getSelf().avatar.url
            }

            footer {
                val versions = it.discord.versions
                text = "DiscordKt: ${versions.library} - Kord: ${versions.kord} - Kotlin: ${versions.kotlin}"
            }

            addField("Prefix", it.prefix())
        }

        //Determine if the given command can be run with these conditions.
        permissions {
            true
        }

        //The Discord presence shown on your bot.
        presence {
            playing(defaultPrefix)
        }

        //This is run once the bot has finished setup and logged in.
        onStart {
            val guilds = kord.guilds.toList().joinToString { it.name }
            println("Guilds: $guilds")
        }
    }
}