LIBRARY ieee;
USE ieee.std_logic_1164.ALL;
 
--Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--USE ieee.numeric_std.ALL;
 
ENTITY Lab4Simulation IS
END Lab4Simulation;
 
ARCHITECTURE behavior OF Lab4Simulation IS 
 
    -- Component Declaration for the Unit Under Test (UUT)
 
    COMPONENT Lab4Code
    PORT(
         PushBt1 : IN  std_logic;
         PushBt2 : IN  std_logic;
         MCLK : IN  std_logic;
         LedDisplay : OUT  std_logic_vector(9 downto 0);
         SevenSegment1 : OUT  std_logic_vector(6 downto 0);
         SevenSegment2 : OUT  std_logic_vector(6 downto 0)
        );
    END COMPONENT;
    

   signal PushBt1 : std_logic := '0';
   signal PushBt2 : std_logic := '0';
   signal MCLK : std_logic := '0';

   signal LedDisplay : std_logic_vector(9 downto 0);
   signal SevenSegment1 : std_logic_vector(6 downto 0);
    signal SevenSegment2 : std_logic_vector(6 downto 0);

   constant MCLK_period : time :=10 ns;
 
BEGIN
    uut: Lab4Code PORT MAP (
          PushBt1 => PushBt1,
          PushBt2 => PushBt2,
          MCLK => MCLK,
          LedDisplay => LedDisplay,
          SevenSegment1 => SevenSegment1,
          SevenSegment2 => SevenSegment2
        );
   MCLK_process :process
   begin
		MCLK <= '0';
		wait for MCLK_period/2;
		MCLK <= '1';
		wait for MCLK_period/2;
   end process;
   stim_proc: process
   begin  

      wait;
   end process;
	PushBt1<='0','1' after MCLK_period/2;
	PushBt2<='0','1' after MCLK_period/2;
	
END;

