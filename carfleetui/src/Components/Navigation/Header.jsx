import React from "react";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import IconButton from "@mui/material/IconButton";
import { useNavigate } from "react-router-dom";

function Header(props) {
  const navigate = useNavigate();
  return (
    <AppBar position="static" style={{ backgroundColor: "#FC6A03" }}>
      <Toolbar>
        <IconButton edge="start" color="inherit" aria-label="logo">
          <img
            alt="Logo"
            style={{ width: 100 }}
            src="https://images.unsplash.com/photo-1549317661-bd32c8ce0db2?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxleHBsb3JlLWZlZWR8MTh8fHxlbnwwfHx8fHw%3D&auto=format&fit=crop&w=500&q=60"
          />{" "}
        </IconButton>

        <Typography
          variant="h6"
          component="div"
          sx={{ flexGrow: 1, textAlign: "center" }}
        >
          {props.greetings}
        </Typography>

        <Button
          color="inherit"
          onClick={() => {
            navigate("/login");
          }}
        >
          SignIn
        </Button>
        <Button
          color="inherit"
          onClick={() => {
            navigate("/register");
          }}
        >
          SignUp
        </Button>
      </Toolbar>
    </AppBar>
  );
}

export default Header;
