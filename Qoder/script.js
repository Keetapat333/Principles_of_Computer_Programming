document.addEventListener('DOMContentLoaded', () => {
    // Game elements
    const gameBoard = document.getElementById('gameBoard');
    const movesDisplay = document.getElementById('moves');
    const timerDisplay = document.getElementById('timer');
    const restartBtn = document.getElementById('restartBtn');
    const winMessage = document.getElementById('winMessage');
    const finalMoves = document.getElementById('finalMoves');
    const finalTime = document.getElementById('finalTime');

    // Game state
    let cards = [];
    let flippedCards = [];
    let matchedPairs = 0;
    let moves = 0;
    let timer = 0;
    let timerInterval = null;
    let gameStarted = false;
    let hintCount = 3;
    
    // High scores
    let highScores = {
        easy: {moves: Infinity, time: Infinity},
        medium: {moves: Infinity, time: Infinity},
        hard: {moves: Infinity, time: Infinity}
    };

    // Card symbols (emojis)
    const cardSymbolSets = {
        easy: ['🍎', '🍌', '🍇', '🍓', '🍊', '🍋'],
        medium: ['🍎', '🍌', '🍇', '🍓', '🍊', '🍋', '🍒', '🍑'],
        hard: ['🍎', '🍌', '🍇', '🍓', '🍊', '🍋', '🍒', '🍑', '🥝', '🥭', '🍍', '🥥']
    };

    // Load high scores from localStorage
    function loadHighScores() {
        const savedScores = localStorage.getItem('cardMatchHighScores');
        if (savedScores) {
            highScores = JSON.parse(savedScores);
        }
    }
    
    // Save high scores to localStorage
    function saveHighScores() {
        localStorage.setItem('cardMatchHighScores', JSON.stringify(highScores));
    }
    
    // Display high scores
    function updateHighScoreDisplay() {
        const difficulty = document.getElementById('difficulty').value;
        const highScoreMoves = highScores[difficulty].moves;
        const highScoreTime = highScores[difficulty].time;
        
        if (highScoreMoves !== Infinity) {
            document.querySelector('.high-score-moves').textContent = highScoreMoves;
            document.querySelector('.high-score-time').textContent = highScoreTime;
            document.querySelector('.high-scores').classList.remove('hidden');
        } else {
            document.querySelector('.high-scores').classList.add('hidden');
        }
    }
    
    // Initialize the game
    function initGame() {
        // Load high scores
        loadHighScores();
        
        // Show loading animation
        document.getElementById('loading').classList.remove('hidden');
        
        // Delay initialization to show loading animation
        setTimeout(() => {
            // Get selected difficulty
            const difficulty = document.getElementById('difficulty').value;
            const cardSymbols = cardSymbolSets[difficulty];
            const totalPairs = cardSymbols.length;
            
            // Update UI elements based on difficulty
            document.getElementById('totalPairs').textContent = totalPairs;
            document.getElementById('matches').textContent = '0';
            
            // Set grid columns based on difficulty
            if (difficulty === 'easy') {
                gameBoard.style.gridTemplateColumns = 'repeat(4, 1fr)';
                gameBoard.style.maxWidth = '500px';
            } else if (difficulty === 'medium') {
                gameBoard.style.gridTemplateColumns = 'repeat(4, 1fr)';
                gameBoard.style.maxWidth = '600px';
            } else if (difficulty === 'hard') {
                gameBoard.style.gridTemplateColumns = 'repeat(6, 1fr)';
                gameBoard.style.maxWidth = '700px';
            }
            
            // Reset game state
            gameBoard.innerHTML = '';
            flippedCards = [];
            matchedPairs = 0;
            moves = 0;
            timer = 0;
            movesDisplay.textContent = moves;
            timerDisplay.textContent = timer;
            winMessage.classList.add('hidden');
            hintCount = 3; // Reset hint count
            
            // Clear any existing timer
            if (timerInterval) {
                clearInterval(timerInterval);
            }
            
            gameStarted = false;
            
            // Create card pairs
            let gameCards = [...cardSymbols, ...cardSymbols];
            shuffleArray(gameCards);
            
            // Create card elements
            gameCards.forEach((symbol, index) => {
                const card = document.createElement('div');
                card.className = 'card';
                card.dataset.symbol = symbol;
                card.dataset.index = index;
                
                const cardFront = document.createElement('div');
                cardFront.className = 'card-face card-front';
                cardFront.textContent = symbol;
                
                const cardBack = document.createElement('div');
                cardBack.className = 'card-face card-back';
                cardBack.textContent = '?';
                
                card.appendChild(cardFront);
                card.appendChild(cardBack);
                
                card.addEventListener('click', flipCard);
                gameBoard.appendChild(card);
            });
            
            cards = Array.from(document.querySelectorAll('.card'));
            
            // Hide loading animation
            document.getElementById('loading').classList.add('hidden');
            
            // Update high score display
            updateHighScoreDisplay();
            
            // Update hint display
            updateHintDisplay();
        }, 500);
    }

    // Shuffle array using Fisher-Yates algorithm
    function shuffleArray(array) {
        for (let i = array.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [array[i], array[j]] = [array[j], array[i]];
        }
        return array;
    }

    // Play sound function
    function playSound(soundId) {
        try {
            const sound = document.getElementById(soundId);
            if (sound) {
                sound.currentTime = 0;
                sound.play().catch(e => console.log("Audio play failed:", e));
            }
        } catch (e) {
            console.log("Error playing sound:", e);
        }
    }

    // Flip a card
    function flipCard() {
        // Don't allow flipping if two cards are already flipped or if card is already matched
        if (flippedCards.length === 2 || this.classList.contains('matched') || flippedCards.includes(this)) {
            return;
        }
        
        // Start timer on first move
        if (!gameStarted) {
            startTimer();
            gameStarted = true;
        }
        
        // Play flip sound
        playSound('flipSound');
        
        // Flip the card
        this.classList.add('flipped');
        flippedCards.push(this);
        
        // Check for match when two cards are flipped
        if (flippedCards.length === 2) {
            moves++;
            movesDisplay.textContent = moves;
            
            const card1 = flippedCards[0].dataset.symbol;
            const card2 = flippedCards[1].dataset.symbol;
            
            if (card1 === card2) {
                // Match found
                setTimeout(() => {
                    flippedCards[0].classList.add('matched');
                    flippedCards[1].classList.add('matched');
                    flippedCards = [];
                    matchedPairs++;
                    
                    // Update matches display
                    document.getElementById('matches').textContent = matchedPairs;
                    
                    // Play match sound
                    playSound('matchSound');
                    
                    // Check for win
                    const difficulty = document.getElementById('difficulty').value;
                    const cardSymbols = cardSymbolSets[difficulty];
                    if (matchedPairs === cardSymbols.length) {
                        endGame();
                    }
                }, 500);
            } else {
                // No match, flip cards back
                setTimeout(() => {
                    flippedCards[0].classList.remove('flipped');
                    flippedCards[1].classList.remove('flipped');
                    flippedCards = [];
                }, 1000);
            }
        }
    }

    // Start the game timer
    function startTimer() {
        timerInterval = setInterval(() => {
            timer++;
            timerDisplay.textContent = timer;
        }, 1000);
    }

    // End the game
    function endGame() {
        clearInterval(timerInterval);
        
        // Add a small delay for dramatic effect
        setTimeout(() => {
            finalMoves.textContent = moves;
            finalTime.textContent = timer;
            winMessage.classList.remove('hidden');
            
            // Play win sound
            playSound('winSound');
            
            // Update high scores if needed
            const difficulty = document.getElementById('difficulty').value;
            let newRecord = false;
            
            if (moves < highScores[difficulty].moves) {
                highScores[difficulty].moves = moves;
                newRecord = true;
            }
            
            if (timer < highScores[difficulty].time) {
                highScores[difficulty].time = timer;
                newRecord = true;
            }
            
            if (newRecord) {
                saveHighScores();
                updateHighScoreDisplay();
            }
            
            // Add celebration effect to all cards
            cards.forEach(card => {
                card.classList.add('pulse');
            });
        }, 1000);
    }

        // Hint system
    function useHint() {
        if (hintCount <= 0) return;
        
        // Find two unflipped cards with the same symbol that aren't matched
        const unflippedCards = cards.filter(card => 
            !card.classList.contains('flipped') && 
            !card.classList.contains('matched')
        );
        
        if (unflippedCards.length < 2) return;
        
        // Group unflipped cards by symbol
        const cardsBySymbol = {};
        unflippedCards.forEach(card => {
            const symbol = card.dataset.symbol;
            if (!cardsBySymbol[symbol]) {
                cardsBySymbol[symbol] = [];
            }
            cardsBySymbol[symbol].push(card);
        });
        
        // Find a symbol that has at least 2 unflipped cards
        let foundPair = null;
        for (const symbol in cardsBySymbol) {
            if (cardsBySymbol[symbol].length >= 2) {
                foundPair = cardsBySymbol[symbol].slice(0, 2);
                break;
            }
        }
        
        if (foundPair) {
            // Temporarily flip the pair
            foundPair[0].classList.add('flipped');
            foundPair[1].classList.add('flipped');
            
            // Play flip sound
            playSound('flipSound');
            
            // Show the hint for a short time
            setTimeout(() => {
                // Only flip back if they haven't been matched
                if (!foundPair[0].classList.contains('matched')) {
                    foundPair[0].classList.remove('flipped');
                }
                if (!foundPair[1].classList.contains('matched')) {
                    foundPair[1].classList.remove('flipped');
                }
            }, 1000);
            
            // Decrement hint count
            hintCount--;
            updateHintDisplay();
        }
    }
    
    // Update hint display
    function updateHintDisplay() {
        const hintBtn = document.getElementById('hintBtn');
        hintBtn.textContent = `Hint (${hintCount} left)`;
        
        if (hintCount <= 0) {
            hintBtn.disabled = true;
        }
    }
    
    // Event listener for restart button
    restartBtn.addEventListener('click', initGame);
    
    // Event listener for difficulty selector
    document.getElementById('difficulty').addEventListener('change', initGame);
    
    // Event listener for hint button
    document.getElementById('hintBtn').addEventListener('click', useHint);

    // Initialize the game on page load
    initGame();
});