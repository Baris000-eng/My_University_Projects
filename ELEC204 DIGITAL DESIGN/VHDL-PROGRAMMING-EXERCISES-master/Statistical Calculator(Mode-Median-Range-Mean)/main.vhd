library IEEE;
use ieee.numeric_std.all;
use IEEE.STD_LOGIC_1164.ALL;

entity Main is
    Port ( --clock for processes
			  clk : in STD_LOGIC;
	         
			  -- switchs for inputting numbers
			  switch0: in STD_LOGIC;
			  switch1: in STD_LOGIC;
			  switch2: in STD_LOGIC;
			  switch3: in STD_LOGIC;
			 
			  -- switchs for assigning value of modeSelect
			  switch8: in STD_LOGIC;
			  switch9: in STD_LOGIC;
			  
			  --switchs for saving each number
			  num1EnteredSwitch: in STD_LOGIC;
			  num2EnteredSwitch: in STD_LOGIC;
			  num3EnteredSwitch: in STD_LOGIC;
			  num4EnteredSwitch: in STD_LOGIC;
			  
			  -- output
			  result : out  STD_LOGIC_VECTOR (3 downto 0)
			  );
			 
end Main;

architecture Behavioral of Main is

	--to sum 2 4-bit numbers
	component adder4bit4bit
		Port ( cin : in  STD_LOGIC;
			  num1 : in  STD_LOGIC_VECTOR (3 downto 0);
           num2 : in  STD_LOGIC_VECTOR (3 downto 0);
			  result : out  STD_LOGIC_VECTOR (6 downto 0);
           Cout : out  STD_LOGIC);
	end component;
	
	--to sum 7-bit 4-bit numbers
	component adder7bit4bit
		Port ( cin : in  STD_LOGIC;
			  num1 : in  STD_LOGIC_VECTOR (6 downto 0);
           num2 : in  STD_LOGIC_VECTOR (3 downto 0);
			  result : out  STD_LOGIC_VECTOR (6 downto 0);
           Cout : out  STD_LOGIC);
	end component;
	
	
	component dividerBy4
		Port ( 
			  num : in  STD_LOGIC_VECTOR (6 downto 0);
           result : inout  STD_LOGIC_VECTOR (3 downto 0));
	end component;
	
	
	component dividerBy2
		Port ( 
			  num : in  STD_LOGIC_VECTOR (4 downto 0);
           result : inout  STD_LOGIC_VECTOR (3 downto 0));
	end component;
	
	
	component comparator
    Port ( A : in  STD_LOGIC_VECTOR (3 DOWNTO 0);
           B : in  STD_LOGIC_VECTOR (3 DOWNTO 0);
           GAB : out  STD_LOGIC);
	end component;
	
	
	component subtractor4bit4bit
		Port (
			  num1 : in  STD_LOGIC_VECTOR (3 downto 0);
           num2 : in  STD_LOGIC_VECTOR (3 downto 0);
			  result : out  STD_LOGIC_VECTOR (3 downto 0));
	end component;
	
	--to add 1 to 4-bit number
	component oneAdderTo4Bit 
	  Port (
	  num1 : in  STD_LOGIC_VECTOR (3 DOWNTO 0);
     result : out  STD_LOGIC_VECTOR (3 DOWNTO 0)
	  );
	end component;
	
	component sum2Medians
		Port ( 
			  num1 : in  STD_LOGIC_VECTOR (3 downto 0);
           num2 : in  STD_LOGIC_VECTOR (3 downto 0);
			  result : out  STD_LOGIC_VECTOR (4 downto 0)
           );
	end component;


	-- numbers
	SIGNAL num1 : STD_LOGIC_VECTOR (3 downto 0);
	SIGNAL num2 : STD_LOGIC_VECTOR (3 downto 0);
	SIGNAL num3 : STD_LOGIC_VECTOR (3 downto 0);
	SIGNAL num4 : STD_LOGIC_VECTOR (3 downto 0);


	-- signal which decide to operation (00=mean, 01=mode, 10=median, 11=range)
	SIGNAL modeSelect : STD_LOGIC_VECTOR(1 downto 0);
	
	SIGNAL mean : STD_LOGIC_VECTOR (3 downto 0);
	SIGNAL mode :STD_LOGIC_VECTOR (3 downto 0);
	SIGNAL median: STD_LOGIC_VECTOR(3 downto 0);
	SIGNAL rangee :  STD_LOGIC_VECTOR (3 downto 0);
	
	-- sums which are used for mean
	SIGNAL sum1 : STD_LOGIC_VECTOR (6 downto 0):="0000000";
	SIGNAL sum2 : STD_LOGIC_VECTOR (6 downto 0):="0000000";
	SIGNAL sum3 : STD_LOGIC_VECTOR (6 downto 0):="0000000";

	-- result of comparisons (if Gij=1, it means i>j)
	SIGNAL G12 : STD_LOGIC;
	SIGNAL G13 : STD_LOGIC;
	SIGNAL G14 : STD_LOGIC;
	SIGNAL G23 : STD_LOGIC;
	SIGNAL G24 : STD_LOGIC;
	SIGNAL G34 : STD_LOGIC;

	
	--for range
	SIGNAL max : STD_LOGIC_VECTOR (3 downto 0):="0000"; 
	SIGNAL min : STD_LOGIC_VECTOR (3 downto 0):="0000"; 
	SIGNAL cout : STD_LOGIC_VECTOR(7 downto 1);
	signal rangeminus1 : STD_LOGIC_VECTOR (3 downto 0); 
	
	--for median
	SIGNAL median1 : STD_LOGIC_VECTOR (3 downto 0);
	SIGNAL median2 : STD_LOGIC_VECTOR (3 downto 0);
	SIGNAL median1PlusMedian2 : STD_LOGIC_VECTOR (4 downto 0);
	SIGNAL coutForMedianSum : STD_LOGIC;

	--for process which calculates ranks
	SIGNAL en : STD_LOGIC :='1';
	--for process which calculates counts
	SIGNAL en2 : STD_LOGIC :='1';
	--count of number of inputted. (to start the calculations when all numbers are inputted.)
	SIGNAL numCounter : INTEGER := 0;
	
	--counters to use in processes which calculate ranks and counts.
	SIGNAL counter : INTEGER := 1;
	SIGNAL counter2 : INTEGER := 1;
	
	-- ranks of the numbers (number with rank 11 is biggest, number with rank 00 is smallest.)
	SIGNAL rank1 : UNSIGNED (1 downto 0) := "00";
	SIGNAL rank2 : UNSIGNED (1 downto 0) := "00";
	SIGNAL rank3 : UNSIGNED (1 downto 0) := "00";
	SIGNAL rank4 : UNSIGNED (1 downto 0) := "00";

	-- counts (how many times repeated) of the numbers (used for mode)
	SIGNAL count1 : UNSIGNED (1 downto 0) := "00";
	SIGNAL count2 : UNSIGNED (1 downto 0) := "00";
	SIGNAL count3 : UNSIGNED (1 downto 0) := "00";
	SIGNAL count4 : UNSIGNED (1 downto 0) := "00";
	SIGNAL maxcount : UNSIGNED (1 downto 0) := "00";
	
	
	--enable inputs for taking numbers from switchs.
	SIGNAL num1en: STD_LOGIC :='1';
	SIGNAL num2en: STD_LOGIC :='1';
	SIGNAL num3en :STD_LOGIC :='1';
	SIGNAL num4en :STD_LOGIC :='1';
	SIGNAL allNumbersTaken : STD_LOGIC :='0';
	
	
begin


	modeSelect(0)<=switch8;
	modeSelect(1)<=switch9;
   
	--sum each number one by one
	summing1:  adder4bit4bit  port map (  '0'  , num1, num2, sum1, cout(1));
	summing2:  adder7bit4bit port map (cout(1), sum1, num3, sum2, cout(2));
	summing3:  adder7bit4bit port map (cout(2), sum2, num4, sum3, cout(3));

	--divide by 4
	calculateMean: dividerBy4 port map (sum3, mean);
	----mean ends----
	
	--compare numbers (used for rank calculations)
	calculateG12: comparator port map (num1, num2, G12);
	calculateG13: comparator port map (num1, num3, G13);
	calculateG14: comparator port map (num1, num4, G14);

	calculateG23: comparator port map (num2, num3, G23);
	calculateG24: comparator port map (num2, num4, G24);

	calculateG34: comparator port map (num3, num4, G34);


	--processes which take the number inputs from switches and assign them to num1, num2, num3 and num4.
	process (num1EnteredSwitch)
	begin
			if(num1en='1' and num1EnteredSwitch='1') then
				num1(0)<=switch0;
				num1(1)<=switch1;
				num1(2)<=switch2;
				num1(3)<=switch3;
				num1en<='0';
			end if;
			
	end process;
	
	process (num2EnteredSwitch)
	begin
			if(num2en='1' and num2EnteredSwitch='1') then
				num2(0)<=switch0;
				num2(1)<=switch1;
				num2(2)<=switch2;
				num2(3)<=switch3;
				num2en<='0';
			end if;
			
	end process;
	
	process (num3EnteredSwitch)
	begin
			if(num3en='1' and num3EnteredSwitch='1') then
				num3(0)<=switch0;
				num3(1)<=switch1;
				num3(2)<=switch2;
				num3(3)<=switch3;
				num3en<='0';
			end if;
	end process;
	
	process (num4EnteredSwitch)
	begin
			if(num4en='1' and num4EnteredSwitch='1') then
				num4(0)<=switch0;
				num4(1)<=switch1;
				num4(2)<=switch2;
				num4(3)<=switch3;
				num4en<='0';
				allNumbersTaken<='1';
			end if;
	end process;
			
	
	--ranks are calculated to use for median and range
	process (clk, num4)
	begin

   if (en='1' and counter /= 7 and allNumbersTaken='1') then
		
		if(rising_edge(clk)) then
		
			if(counter = 1) then
				if (G12 = '1') then
					rank1 <= rank1 + 1;
				else 
					rank2 <= rank2 + 1;
				end if;
			elsif (counter = 2) then
				if (G13 = '1') then
					rank1 <= rank1 + 1;
				else
					rank3 <= rank3 + 1;
				end if;
			elsif (counter = 3) then
				if (G14 = '1') then
					rank1 <= rank1 + 1;
				else
					rank4 <= rank4 + 1;
				end if;
			elsif (counter = 4) then
				if (G23 = '1') then
					rank2 <= rank2 + 1;
				else
					rank3 <= rank3 + 1;
				end if;
			elsif (counter = 5) then
				if (G24 = '1') then
					rank2 <= rank2 + 1;
				else
					rank4 <= rank4 + 1;
				end if;

			elsif (counter = 6) then
				if (G34 = '1') then
					rank3 <= rank3 + 1;
				else
					rank4 <= rank4 + 1;
				end if;

			end if;
		 counter <= counter + 1 ;
		 end if;
	 end if;
	 end process;
	 

	 ----range starts----
	 
	 --max num is found.
	 process (rank1,rank2,rank3,rank4)
	 begin
		 if(rank1 = "11") then
			max <= num1;
		 elsif (rank2 = "11") then
			max <= num2;
		 elsif (rank3 = "11") then
			max <= num3;
		 elsif (rank4 = "11") then
			max <= num4;
		 end if;
	 end process;
		
	 --min num is found.
	 process (rank1,rank2,rank3,rank4)
	 begin
		 if(rank1 = "00") then
			min <= num1;
		 elsif (rank2 = "00") then
			min <= num2;
		 elsif (rank3 = "00") then
			min <= num3;
		 elsif (rank4 = "00") then
			min <= num4;
		 end if;
	 end process;
	 
	 --subtract min from max (first compute A+B', then add 1 since A-B = A+B'-1)
	 calculateRangeMinus1: subtractor4bit4bit  port map (max,  min, rangeminus1);
	 calculateRange: oneAdderTo4Bit  port map (rangeminus1, rangee);
	 ----range ends----

	 ----median starts----
	 
	 --find the number with rank 1 and 2 which are middle numbers.
	 process (rank1,rank2,rank3,rank4)
	 begin
		 if(rank1 = "01") then
			median1 <= num1;
		 elsif (rank2 = "01") then
			median1 <= num2;
		 elsif (rank3 = "01") then
			median1 <= num3;
		 elsif (rank4 = "01") then
			median1 <= num4;
		 end if;
	 end process;
	 
	 process (rank1,rank2,rank3,rank4)
	 begin
		 if(rank1 = "10") then
			median2 <= num1;
		 elsif (rank2 = "10") then
			median2 <= num2;
		 elsif (rank3 = "10") then
			median2 <= num3;
		 elsif (rank4 = "10") then
			median2 <= num4;
		 end if;
	 end process;
	
	--sum two middle numbers and divide by two to get median.
	sumMedian1andMedian2: sum2Medians port map (median1, median2, median1PlusMedian2);
	calculateMedian: dividerBy2 port map (median1PlusMedian2, median);
   ----median ends----
	
	----mode starts----
	
	--calculate count of numbers.
	process (clk, num4)
	begin

   if (en2='1' and counter2 /= 7) then
		if(rising_edge(clk) and allNumbersTaken='1') then
			
			if(counter2 = 1) then
				if (num1 = num2 ) then
					count1 <= count1 + 1; 
					count2 <= count2 + 1;
				end if;
			elsif (counter2 = 2) then
				if (num1 = num3 ) then
					count1 <= count1 + 1;
					count3 <= count3 + 1;
				end if;
			elsif (counter2 = 3) then
				if (num1 = num4 ) then
					count1 <= count1 + 1;
					count4 <= count4 + 1;
				end if;

			elsif (counter2 = 4) then
				if (num2 =num3 ) then
					count2 <= count2 + 1;
					count3 <= count3 + 1;
				end if;
			elsif (counter2 = 5) then
				if (num2 = num4 ) then
					count2 <= count2 + 1;
					count4 <= count4 + 1;
				end if;

			elsif (counter2 = 6) then
				if (num3 = num4) then
					count3 <= count3 + 1;
					count4 <= count4 + 1;
				end if;

			en2<='0';
			end if;
		 counter2 <= counter2 + 1 ;
	    end if;
	 end if;
	 end process;
	
	--find the largest count
	process (clk, count1, count2, count3, count4)
	begin
	 
   
		if(rising_edge(clk)) then
			
				if (unsigned(count1)>unsigned(maxcount)) then
					maxcount<=count1;
				end if;
			
				if (unsigned(count2)>unsigned(maxcount)) then
					maxcount<=count2;
				end if;
			
				if (unsigned(count3)>unsigned(maxcount)) then
					maxcount<=count3;
				end if;
			
				if (unsigned(count4)>unsigned(maxcount)) then
					maxcount<=count4;
				end if;
	
	 end if;
	 end process;
	 
	 --assign the value of mode to number which has the largest count.
	 process ( maxcount,count1,count2,count3,count4)
	 begin
		 if(maxcount = count1) then
			mode <= num1;
		 elsif (maxcount = count2) then
			mode <= num2;
		 elsif (maxcount = count3) then
			mode <= num3;
		 elsif (maxcount = count4) then
			mode <= num4;
		 end if;
	 end process;
	----mode ends----
	
	--assign result's value depending on the value of modeSelect.
	result <=  mean when modeSelect = "00" else 
				  mode when modeSelect = "01" else
				  median when modeSelect = "10" else
				  rangee;
						
end Behavioral;