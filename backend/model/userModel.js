const mongoose = require("mongoose");

const userSchema = mongoose.Schema(
  {
    gmail: {
      type: String,
      required: [true, "Please add a Gmail"],
      unique: true,
    },
    password: {
      type: String,
      required: [true, "Please add a password"],
    },
  },
  {
    timestamps: true,
  }
);

module.exports = mongoose.model("User", userSchema);
