import { Box } from "@mui/material";
import { Outlet } from "react-router-dom";

export default function ManagerCustomerLayout() {
    return (
        <Box>
            {/* <Header greetings={"Manage Cars"} /> */}

            <main>
                <Outlet />
            </main>
        </Box>
    );
}
