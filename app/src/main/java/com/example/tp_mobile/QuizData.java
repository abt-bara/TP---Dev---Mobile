
package com.example.tp_mobile;

public class QuizData {

    /*
     * QUIZZES[quiz][question] = {
     *     question,
     *     option 1,
     *     option 2,
     *     correct answer index
     * }
     *
     * Correct answer index:
     * 0 = first option
     * 1 = second option
     */

    // Names displayed for each quiz
    public static final String[] QUIZ_NAMES = {
            "Stories of the Prophets",
            "Prophets in the Quran",
            "AI Basics",
            "Machine Learning",
            "Car Brands",
            "Car Engines"
    };

    // Questions for each quiz
    public static final String[][][] QUIZZES = {

            // 0: Stories of the Prophets
            {
                    {"Which prophet was swallowed by a whale?", "Yunus", "Musa", "0"},
                    {"Which prophet received the Torah?", "Musa", "Isa", "0"},
                    {"Which prophet is called Khalilullah (Friend of Allah)?", "Yusuf", "Ibrahim", "1"},
                    {"Which prophet built the Kaaba with his son Ismail?", "Nuh", "Ibrahim", "1"}
            },

            // 1: Prophets in the Quran
            {
                    {"Which prophet was known for his beautiful voice when reciting?", "Dawud", "Yunus", "0"},
                    {"Which prophet was thrown into the fire?", "Ibrahim", "Musa", "0"},
                    {"Which prophet was given the ability to interpret dreams?", "Yusuf", "Sulaiman", "0"},
                    {"Which prophet spoke to Allah directly?", "Musa", "Isa", "0"}
            },

            // 2: AI Basics
            {
                    {"What does AI stand for?", "Artificial Intelligence", "Automatic Internet", "0"},
                    {"Which of these is an AI chatbot?", "Excel", "Claude", "1"},
                    {"What can AI systems learn from?", "Data", "Nothing", "0"},
                    {"What is a common use of AI?", "Image recognition", "Cooking food physically", "0"}
            },

            // 3: Machine Learning
            {
                    {"Machine learning models learn from...", "Data", "Magic", "0"},
                    {"Training a model means...", "Deleting data", "Teaching it with examples", "1"},
                    {"What is used to train a machine learning model?", "Examples and data", "Only electricity", "0"},
                    {"What does a trained model produce?", "Predictions", "Physical objects", "0"}
            },

            // 4: Car Brands
            {
                    {"Who makes the Corolla?", "Toyota", "Honda", "0"},
                    {"Which brand is German?", "Kia", "BMW", "1"},
                    {"Which company makes the Civic?", "Honda", "Toyota", "0"},
                    {"Which brand makes the Mustang?", "Ford", "BMW", "0"}
            },

            // 5: Car Engines
            {
                    {"Which engine uses spark plugs?", "Petrol", "Diesel", "0"},
                    {"Electric cars store energy in a...", "Fuel tank", "Battery", "1"},
                    {"Which fuel is commonly used by diesel engines?", "Diesel", "Petrol", "0"},
                    {"What does an engine convert into motion?", "Energy", "Water", "0"}
            }
    };
}
