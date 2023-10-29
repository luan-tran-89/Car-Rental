import { useState } from "react";
import { useNavigate } from "react-router-dom";

import PeopleAltIcon from "@mui/icons-material/PeopleAlt";
import ElectricCarIcon from "@mui/icons-material/ElectricCar";
// import AddReactionIcon from "@mui/icons-material/AddReaction";
import CommuteIcon from "@mui/icons-material/Commute";
import Tabs from "../ResuableComponents/Tabs";

export function ManagerTab() {
  const [activeTab, setActiveTab] = useState(0);
  const navigate = useNavigate();

  const tabsData = [
    {
      label: "Customers",
      icon: <PeopleAltIcon />,
      route: "/manager/customers",
    },
    { label: "Cars", icon: <CommuteIcon />, route: "/manager/cars" },
    // {
    //   label: "Add Customer",
    //   icon: <AddReactionIcon />,
    //   route: "/manager/addCustomer",
    // },
    { label: "Add Car", icon: <ElectricCarIcon />, route: "/manager/addCar" },
  ];

  const handleTabChange = (index) => {
    setActiveTab(index);
    navigate(tabsData[index].route);
  };

  return (
    <Tabs
      tabs={tabsData}
      activeTabIndex={activeTab}
      onTabChange={handleTabChange}
    />
  );
}
