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
  "exclude": [
      "minecraft:golden_apple",
      "minecraft:enchanted_golden_apple"
  ],
  "foodStates": [
      {
          "tooltip": "Fresh",
          "color": 5635925,
          "duration": 1,
          "nurtition": "x",
          "saturation": "x",
          "eatSeconds": "x"
      },
      {
          "tooltip": "Edible",
          "color": 16777045,
          "duration": 3,
          "nurtition": "x/2",
          "saturation": "x/2",
          "eatSeconds": "x"
      },
      {
          "tooltip": "Rotten",
          "color": 16733525,
          "duration": -1,
          "nurtition": "x*0",
          "saturation": "x*0",
          "eatSeconds": "x*2"
      }
  ]
}
```

Here are a thigs you should know before tampering with the config:
- All Food Stages must be in order. The highest one comes first.
- Values for nutrition, saturation, eatSeconds are essentially equations, where x is the original value of the given item. They can be anything, ranging from "(x / 2) + 1" to a plain number like "2". Mark that x value is not affected by the previous stage as it is the original value.
- Duration is measured in world days(Which means even if you do /time set [insert something crazy] all your food won't immediately rot)
- Color is an rgb integer.
- When "modDataComponentsEnabled" is set to false the mod will actively remove the rotting functionality from every item it can tick. Essentialy all the food in players inventory will reset and become normal food that doesn't rot.
- randomiseFoodState currently doesn't work, so don't bother.

## Why was this mod made?
Truthfully i just wanted to make my friends to suffer. And i thought maybe someone might have a simillar desire...

As always, any mistakes are my own.
