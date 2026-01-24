# Rotting Food
This fully customizable mod adds a rotting mechanic to all foods in the game, even to ones added by other mods. No more chest filled with food. No more food farms!

![A lot of Baked Potatoes with a "Rotten" Tooltip](https://cdn.modrinth.com/data/cached_images/2bdd9e58fa7c3eb66564fc862e2380f187b4a5ca.png)

## Configuration Guide
This mod was made to be extremely customizable, which means you can freely change what stages the food will go through as it rots, as well as which food will rot and which won't.
This is the default config:
```
{
"modDataComponentsEnabled": true,
"showInTooltip": true,
"randomiseFoodState": false,
"preventFoodFarms": true,
"maxRandomState": 2,
"exclude": [
    "minecraft:golden_apple",
    "minecraft:enchanted_golden_apple",
    "minecraft:golden_carrot"
],
"foodStates": [
    {
    "tooltip": "Fresh",
    "color": 5635925,
    "duration": 1,
    "nurtition": "x",
    "saturation": "x",
    "eatSeconds": "x",
    "effects": []
    },
    {
    "tooltip": "Edible",
    "color": 16777045,
    "duration": 3,
    "nurtition": "x/2",
    "saturation": "x/2",
    "eatSeconds": "x",
    "effects": []
    },
    {
    "tooltip": "Rotten",
    "color": 16733525,
    "duration": -1,
    "nurtition": "0",
    "saturation": "0",
    "eatSeconds": "x*2",
    "effects": [
        {
        "probability": 1.0,
        "id": "minecraft:nausea",
        "duration": 200,
        "amplifier": 1,
        "ambient": true,
        "showParticles": false,
        "showIcon": false
        }
    ]
    }
]
}
```

Here are a things you should know before tampering with the config:
- All Food Stages must be in order. The highest one comes first.
- Values for nutrition, saturation, eatSeconds are essentially equations, where x is the original value of the given item. They can be anything, ranging from "(x / 2) + 1" to a plain number like "2". Mark that x value is not affected by the previous stage as it is the original value.
- Duration is measured in world days(Which means even if you do /time set [insert some crazy number] all your food won't immediately rot)
- Color is an rgb integer.
- Effects allow you to add your own effects to a certain stage(The food will still keep it's original effects).
- When "modDataComponentsEnabled" is set to false the mod will actively remove the rotting functionality from every item it can tick. Essentialy all the food in players inventory will reset and become normal food that doesn't rot.
- randomiseFoodState randomises the food items state when you get it, within the range of maxRandomState. maxRandomState of 2 means you would get either 1 - Fresh or 2 - Edible.

## Why was this mod made?
Truthfully i just wanted to make my friends to suffer. And i thought maybe someone might have a simillar desire or might add this to his hardcore world...

As always, any mistakes are my own.

![Static Badge](https://img.shields.io/badge/Modrinth-f?logo=modrinth&color=green&link=https%3A%2F%2Fmodrinth.com%2Fmod%2Frotting-food)
![Static Badge](https://img.shields.io/badge/CurseForge-f?logo=curseforge&color=orange&link=https%3A%2F%2Fwww.curseforge.com%2Fminecraft%2Fmc-mods%2Frotting-food)
![Static Badge](https://img.shields.io/badge/Mod%20Loaders-Fabric-orange?color=blue&link=https%3A%2F%2Fwww.curseforge.com%2Fminecraft%2Fmc-mods%2Frotting-food)
