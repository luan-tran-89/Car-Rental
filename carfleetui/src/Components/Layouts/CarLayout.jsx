import { Box } from "@mui/material";
import { Outlet } from "react-router-dom";
import Header from "../Navigation/Header";

export default function CarLayout() {
  return (
    <Box>
      {/* <Header greetings={"Manage Cars"} /> */}

      <main>
        <Outlet />
      </main>
    </Box>
  );
}
